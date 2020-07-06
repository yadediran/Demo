package com.os.ovs.appservices;

import com.cadenzauk.core.function.Function1;
import com.cadenzauk.core.lang.CompositeAutoCloseable;
import com.cadenzauk.core.tuple.Tuple2;
import com.cadenzauk.siesta.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

import static com.cadenzauk.siesta.Order.DESC;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class UserDb2Dao<UserRepository> implements UserDao {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserDb2Dao.class);
    private static final long IP_ADDRESS = 0;
    private static final int LOCATION_THRESHOLD = 50;
    private static final double LOCATION = 0;
    private static final String LAST_NAME = "";
    private static final String First_NAME = "";
    private static final double LONGITUDE = 0;
    private static final double LATITUDE = 0;

    private final Database database;
    private final UserRepository userRepository;




    public UserDb2Dao(Database database, UserRepository userRepository) {
        this.database = database;
        this.userRepository = userRepository;


    }
    @Override
    public Optional<Location> getLocation(long userId) {
        List<User> users = getUserDetails(userId);
        return database.from(CityRow.class, "cr")
                .join(UserRow.class, "ur")
                .on(CityRow::userId).isEqualTo(CityRow::userId)
                .where("cr", CityRow::userId).isEqualTo(
                        database.from(UserRow.class, "cr")
                                .where("ur", UserRow::userId).isEqualTo(userId)
                                .orderBy("ur", UserRow::userId, DESC)
                                .fetchFirst(1))
                .and("ru", UserRow::userId).isEqualTo(userId)
                .optional()
                .map(t -> Location.getBuilder()
                        .userId(t.item1().userId())
                        .ipAddress(t.item1().ipAddress())
                        .longitude(t.item1().longitude())
                        .latitude(t.item1().latitude())
                        .build());
    }

    /*private  Location buildLocation(CityRow cityRow, List<User> users){
        return Location.getBuilder()
                .userId(users.userId())
                .ipAddress(cityRow.ipAddress())
                .longitude(cityRow.longitude())
                .latitude(cityRow.latitude())
                .build();

    }*/

    @Override
    public List<User> getUserDetails(long userId) {
        List<User> users = new ArrayList<>();
        List<Tuple2<UserRow, CityRow>> latestRows;
        try(CompositeAutoCloseable closer = new CompositeAutoCloseable()){
            latestRows = database.from(UserRow.class, "ur")
                    .join(CityRow.class, "cr")
                    .on(CityRow::userId).isEqualTo(UserRow::userId)
                    .and("ur", UserRow::userId).isEqualTo(
                            database.from(UserRow.class, "ur")
                                    .select("ur", CityRow::userId)
                                    .where("cr", CityRow::userId).isEqualTo("cr", CityRow::userId)
                                    .and("cr", CityRow::userId).isEqualTo(userId)
                                    .orderBy("ur", CityRow::latitude, DESC)
                                    .fetchFirst(1))
                    .and("rv", CityRow::userId).isEqualTo(userId)
                    .stream(closer)
                    .collect(toList());
        }
        latestRows.forEach(result -> {
            buildUserDetail(result(users)
            );
        });

        return users;
}

    private List<UserRow> result(List<User> users) {

        return result(users);
    }


    private List<User> buildUserDetail(List<UserRow> userRows ) {
        // Accumulate names of people (users) into a List
        List<User> users = new ArrayList<>();

        //Accumulate rows of people into a list
        Map<Long, List<UserRow>> groupData = userRows.stream().collect(groupingBy(UserRow::userId));

        //group users by Location
        Map<Location, List<User>> byLocation
                = users.stream()
                .collect(Collectors.groupingBy(User::getLocation));


        // Partition People into London and 50miles using a Stream API chain rather than a for loop
        Map<Boolean, List<User>> locationType =
                users.stream()
                        .collect(Collectors.partitioningBy(s -> s.getUser() >= LOCATION_THRESHOLD));

        groupData.keySet().forEach(userId -> {
            List<UserRow> userRows1 = groupData.get(userId);

            if (userRows.size() > 1) {
                userRows1.sort(Comparator.comparing(UserRow::userId).reversed());
                users.add(User.newBuilder()
                        .userId(userRows1.get(1).userId())
                        .first_name(userRows1.get(0).first_name())
                        .last_name(userRows1.get(0).last_name())
                        .city(userRows1.get(0).city())
                        .email(userRows1.get(1).email())
                        .longitude(userRows1.get(0).longitude())
                        .latitude(userRows1.get(0).latitude())
                        .ipAddress(userRows1.get(0).ipAddress())
                        .locationType(LocationType.URBAN)
                        .build());
            } else {
                users.add(User.newBuilder()
                        .userId(userRows1.get(1).userId())
                        .first_name(userRows1.get(0).first_name())
                        .last_name(userRows1.get(0).last_name())
                        .city(userRows1.get(0).city())
                        .email(userRows1.get(1).email())
                        .longitude(userRows1.get(0).longitude())
                        .latitude(userRows1.get(0).latitude())
                        .ipAddress(userRows1.get(0).ipAddress())
                        .locationType(LocationType.RURAL)
                        .build());

            }
        });
        return users;
    }


}



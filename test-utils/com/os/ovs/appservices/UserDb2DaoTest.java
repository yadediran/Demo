package com.os.ovs.appservices;

import com.cadenzauk.siesta.Database;
import com.ms.gtrs.categorize.application.config.CategorizeServiceRootConfig;
import com.sun.org.apache.xerces.internal.util.PropertyState;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

import static com.os.ovs.appservices.Location.*;
import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles()
@ContextConfiguration(classes = {CategorizeServiceRootConfig.class})
@TestPropertySource({"classpath:application.properties", "classpath:application-test.properties"})
@DirtiesContext
public class UserDb2DaoTest {

    private static final long USER_ID = 1;
    private static final double LONGITUDE = 0;
    private static final double LATITUDE = 0;
    private static final InetAddress IP_ADDRESS = null;
    private static final String USER_DAO = "";
    private static final String FIRST_NAME = "Jo";
    private static final String LAST_NAME = "Smith";



    @Resource(name = USER_DAO)
    private UserDb2Dao userDb2Dao;

    //sql
    @Resource
    private DataSource dataSource;

    @Resource
    private User userRepository;

    @Resource
    private Database database;

    private long defaultUserId;

    @Before
    public void clearDatabase() throws SQLException{
        userRepository.reset();
    }

   @Test
   public void testRetrieveLocation(){
        Location expectedlocation = getBuilder()
                .userId(USER_ID)
                .ipAddress(IP_ADDRESS)
                .longitude(LONGITUDE)
                .Latitude(LATITUDE)
                .users(Arrays.asList(User.newBuilder()
                        .build()))
                .build();

        final long userId = userDb2Dao.retrieveLocation(expectedlocation);

        database.getDefaultSqlExecutor().update("update user database IP address" +  IP_ADDRESS);

        final Optional<Location> actualLocation = userDb2Dao.getLocation(userId);

        Location expectedLocation = Location.getBuilder()
               .userId(USER_ID)
               .ipAddress(IP_ADDRESS)
               .longitude(LONGITUDE)
               .Latitude(LATITUDE)
               .users(Arrays.asList(User.newBuilder()
                       .cityId(271123)
                       .locationType(LocationType.URBAN)
                       .email("")
                       .first_Name(FIRST_NAME)
                       .last_Name(LAST_NAME)
                       .id(USER_ID)
                       .build()))
                .build();
        assertThat(actualLocation, is(Optional.of(expectedLocation)));

   }

    @Test
    public void testRetrieveMultipleLocation(){
        Location location = getBuilder()
                .cityId(1)
                .ipAddress(IP_ADDRESS)
                .longitude(LONGITUDE)
                .Latitude(LATITUDE)
                .build();

        final long userId = userDb2Dao.retrieveLocation(location);

        database.getDefaultSqlExecutor().update("update user database IP address" +  IP_ADDRESS);



        Location locationUpdated = Location.getBuilder()
                .userId(1)
                .ipAddress(IP_ADDRESS)
                .longitude(LONGITUDE)
                .Latitude(LATITUDE)
                .users(Arrays.asList(User.newBuilder()
                        .cityId((long) 2172797)
                        .locationType(LocationType.RURAL)
                        .email("")
                        .first_name(FIRST_NAME)
                        .last_name(LAST_NAME)
                        .userId(USER_ID)
                        .build()))
                .build();

        final long locationIdupdated = userDb2Dao.addLocation(locationUpdated);

        final Optional<Location> actualLocation = userDb2Dao.getLocation(locationIdupdated);

        Location expectedlocation = location.getBuilder()
                .cityId(locationIdupdated)
                .ipAddress(IP_ADDRESS)
                .longitude(LONGITUDE)
                .Latitude(LATITUDE)
                .users(Arrays.asList(User.newBuilder()
                        .cityId((long) 271123)
                        .locationType(LocationType.URBAN)
                        .email("")
                        .first_name(FIRST_NAME)
                        .last_name(LAST_NAME)
                        .userId(USER_ID)
                        .build()))
                .build();

        assertThat(actualLocation, is(Optional.of(expectedlocation)));

    }

    private void assertThat(Optional<Location> actualLocation, PropertyState propertyState) {
    }


}

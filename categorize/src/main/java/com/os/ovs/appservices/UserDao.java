package com.os.ovs.appservices;

import java.util.List;
import java.util.Optional;

public interface UserDao {


    static User getLocation() {
        return getLocation();
    }

    static List<User> getUserDetails() {
        return getUserDetails();
    }

    Optional<Location> getLocation(long userId);



    List<User> getUserDetails(long userId);
}

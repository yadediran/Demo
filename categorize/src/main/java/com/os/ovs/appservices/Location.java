package com.os.ovs.appservices;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jdk.nashorn.internal.objects.Global;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static java.net.InetAddress.getLocalHost;

@JsonDeserialize(builder = Location.Builder.class)
public class Location  {
    private final List<User> users;
    private static Global StdOut;
    private final String name;
    private final double longitude;
    private final double latitude;

    // create and initialize a point with given name and
    // (latitude, longitude) specified in degrees
    //add parameter constructor
    public Location(Builder builder) {
        this.name = builder.name;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.users = builder.users;

    }

    public Location( String mylocation, double v, double v1, String name, double longitude, double latitude, List<User> users) {
        this.users = Builder.users;
        this.name = Builder.name;
        this.longitude = Builder.longitude;
        this.latitude = Builder.latitude;

    }

    public static void getBuilder() {
        return;
    }

    //provide getters
    public String getName (){return name;}

    public double getLongitude(){return longitude;}

    public double getLatitude(){return latitude;}

    public List<User> getUsers() {return users;}

    public static Builder newBuilder(){ return new Builder();}

    public static Builder toBuilder(Location location){
        return newBuilder()
                .name(location.name)
                .longitude(location.longitude)
                .latitude(location.latitude)
                .users(location.users);

    }

    //inner builder class
    @JsonPOJOBuilder
    public static class Builder {
        private static String name;
        private static double longitude;
        private static double latitude;
        private static List<User> users = new ArrayList<>();
        //private String name;
        //private double longitude;
        //private double latitude;

        @JsonSetter
        public Builder name(String name) {
            Builder.name = name;
            return this;
        }

        @JsonSetter
        public Builder longitude(double longitude) {
            Builder.longitude = longitude;
            return this;
        }

        @JsonSetter
        public Builder latitude(double latitude) {
            Builder.latitude = latitude;
            return this;
        }

        @JsonSetter
        public Builder users (List<User> users){
            this.users = users;
            return this;
        }

        // return string representation of this point
        public String toString() {
            return name + " (" + users +" " + latitude + ", " + longitude + ")";
        }

        //public static <T> int getLocation(T t) {
        //}

        //public static <K, T> K getlocation(T t) {
        //}
        // test client
        public static void main(String[] args) throws UnknownHostException {
            Location location = new Location("London", 40.366633, 74.640832, name, longitude, latitude, users);
            Location location1 = new Location("Mylocation", 42.443087, 76.488707, name, longitude, latitude, users);
            double distance = location.distanceTo(location1);
            Global.print("%6.3f miles from\n", distance);
            Global.println(location + " to " + location1);
            InetAddress inetAddress = getLocalHost();
            Global.println("IP Address:-" + inetAddress.getHostAddress());
            Global.println("Host Name:-" + inetAddress.getHostAddress());
        }
        public Location build() {
            return new Location(this);
        }
    }

    // return distance between this location and that location
    // measured in statute miles
    public double distanceTo(Location myLocation) {
        double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(myLocation.latitude);
        double lon2 = Math.toRadians(myLocation.longitude);

        // get circle distance in radians, using law of cosines formula
        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        // each degree on a great circle of Earth is 60 nautical miles
        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
    }
}
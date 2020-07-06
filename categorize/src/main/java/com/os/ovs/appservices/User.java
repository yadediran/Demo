package com.os.ovs.appservices;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.Optional;


@JsonDeserialize(builder = User.Builder.class)
public class User extends Data implements Comparable<User> {
    private final long userId;
    private final long user;
    private final LocationType locationType;
    private final Optional<String> city;
    private final String first_name;
    private final String last_name;
    private final String email;
    private final double longitude;
    private final double latitude;
    private final Long cityId;


    //add parameter constructor
    public User(Builder builder) {
        this.userId = builder.userId;
        this.cityId = builder.cityId;
        this.locationType = builder.locationType;
        this.user = builder.user;
        this.city = builder.city;
        this.first_name = builder.first_name;
        this.last_name = builder.last_name;
        this.email = builder.email;
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        
    }

    //provide getters
    public long userId(){return userId;}

    public Long getCityId(){return cityId;}

    public long getUser (){return user;}

    public LocationType locationType() {return locationType;}

    public Optional <String> getCity(){return city;}

    public String getFirst_Name(){return first_name;}

    public String getLast_Name(){return last_name;}

    public String getEmail(){return email;}

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public static Builder newBuilder(){ return new Builder();}



    public static Builder toBuilder(User user){
        return newBuilder()
                .userId(user.userId)
                .cityId(user.cityId)
                .user(user.user)
                .locationType(user.locationType)
                .city(user.city)
                .first_name(user.first_name)
                .last_name(user.last_name)
                .email(user.email)
                .longitude(user.longitude)
                .latitude(user.latitude);

    }


    @Override
    public int compareTo(User o) {
        return 0;
    }

    public static <K, T> K getLocation(T t) {
        return (K) t;
    }


    //inner builder class
    @JsonPOJOBuilder
    public static class Builder {
        private Long userId;
        private Long cityId;
        private long user;
        private LocationType locationType;
        private String ipAddress;
        private Optional<String> city = Optional.empty();
        private String first_name;
        private String last_name;
        private String email;
        private double longitude;
        private double latitude;

        @JsonSetter
        public Builder userId(long userId) {
            this.userId = userId;
            return this;
        }

        @JsonSetter
        public Builder cityId(Long cityId) {
            this.cityId = cityId;
            return this;
        }

        @JsonSetter
        public Builder user(long user){
            this.user = user;
            return this;
        }

        @JsonSetter
        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        @JsonSetter
        public Builder city(Optional<String> city) {
            this.city = city;
            return this;
        }

        @JsonSetter
        public Builder first_name(String first_name) {
            this.first_name = first_name;
            return this;
        }

        @JsonSetter
        public Builder last_name(String last_name) {
            this.last_name = last_name;
            return this;
        }

        @JsonSetter
        public Builder email(String email) {
            this.email = email;
            return this;
        }

        @JsonSetter
        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        @JsonSetter
        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        @JsonSetter
        public Builder locationType(LocationType locationType) {
            this.locationType = locationType;
            return this;
        }

        public User build() {
            return build();
        }
    }

}

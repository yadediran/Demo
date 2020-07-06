package com.os.ovs.appservices;

import javax.persistence.Table;


@Table(name = "USER")
public class UserRow extends Data {
    private final long userId;
    private final String ipAddress;
    private final double longitude;
    private final double latitude;
    private final String first_name;
    private final String last_name;
    private final String email;


    private UserRow(UserRowBuilder userRowBuilder) {
        this.userId = userRowBuilder.userId;
        this.ipAddress = userRowBuilder.ipAddress;
        this.longitude = userRowBuilder.longitude;
        this.latitude = userRowBuilder.latitude;
        this.first_name = userRowBuilder.first_name;
        this.last_name = userRowBuilder.last_name;
        this.email = userRowBuilder.email;
    }

    //getters
    public long userId() {
        return userId;
    }

    public String ipAddress() {
        return ipAddress;
    }

    public double longitude() {
        return longitude;
    }

    public double latitude() {
        return latitude;
    }

    public String first_name(){return first_name;}

    public String last_name(){return last_name;}

    public String email(){return email;}

    public static UserRowBuilder newBuilder() {
        return new UserRowBuilder();
    }

    public UserRowBuilder copy() {
        return new UserRowBuilder()
                .userId(userId)
                .ipAddress(ipAddress)
                .longitude(longitude)
                .latitude(latitude)
                .first_name(first_name)
                .last_name(last_name)
                .email(email);
    }

    //inner UserRowBuilder class
    public static final class UserRowBuilder {
        private long userId;
        private String ipAddress;
        private double longitude;
        private double latitude;
        private String first_name;
        private String last_name;
        private String email;

        private UserRowBuilder() {
        }

        public UserRowBuilder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public UserRowBuilder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public UserRowBuilder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public UserRowBuilder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public UserRowBuilder first_name(String first_name) {
            this.first_name = first_name;
            return this;
        }
        public UserRowBuilder last_name(String last_name) {
            this.last_name = last_name;
            return this;
        }

        public UserRowBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserRow build() {
            return new UserRow(this);
        }
    }
}

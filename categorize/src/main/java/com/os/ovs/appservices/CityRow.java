package com.os.ovs.appservices;

import javax.persistence.Table;


@Table(name = "CITY")
public class CityRow extends Data {
    private final long userId;
    private final String ipAddress;
    private final double longitude;
    private final double latitude;
    private final CityRow cityRow;

    private CityRow(CityRowBuilder cityRowBuilder) {
        this.userId = cityRowBuilder.userId;
        this.ipAddress = cityRowBuilder.ipAddress;
        this.longitude = cityRowBuilder.longitude;
        this.latitude = cityRowBuilder.latitude;
        this.cityRow = cityRowBuilder.cityRow;
    }

    //getters
    public long userId() { return userId; }

    public String ipAddress() {
        return ipAddress;
    }

    public double longitude() {
        return longitude;
    }

    public double latitude() {
        return latitude;
    }

    public CityRow cityRow() {
        return cityRow;
    }


    public static CityRowBuilder newBuilder() {
        return new CityRowBuilder();
    }

    public CityRowBuilder copy() {
        return new CityRowBuilder()
                .userId(userId)
                .ipAddress(ipAddress)
                .longitude(longitude)
                .latitude(latitude)
                .cityRow(cityRow);

    }


    //inner CityRowBuilder class
    public static final class CityRowBuilder {
        public CityRow cityRow;
        private Long userId;
        private String ipAddress;
        private double longitude;
        private double latitude;

        private CityRowBuilder() {
        }

        public CityRowBuilder userId(long userId) {
            this.userId = userId;
            return this;
        }

        public CityRowBuilder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public CityRowBuilder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public CityRowBuilder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }


        public CityRowBuilder cityRow(CityRow cityRow) {
            this.cityRow = cityRow;
            return this;

        }

        public CityRow build() {
            return new CityRow(this);
        }


    }
}


package org.openindustries.travel.hotel;

import java.util.UUID;

/** TravelProperty aggregate — Travel property catalogue (hotels, resorts, apartments, hostels). */
public class TravelProperty {
    public UUID id;
        public String name;
        public String propertyType;
        public int roomsCount;
    public String status;

    public static TravelProperty create(String name, String propertyType, int roomsCount) {
        TravelProperty e = new TravelProperty();
        e.id = UUID.randomUUID();
        e.name = name;
        e.propertyType = propertyType;
        e.roomsCount = roomsCount;
        e.status = "ACTIVE";
        return e;
    }
}

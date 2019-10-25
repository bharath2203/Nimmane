package com.example.eio.Models;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RentModel {
    String owner_id, city, district;
    Integer advance, rent;
    Date posted_on;

    public RentModel() {

    }

    public RentModel(String owner_id, String city, String district, Integer advance, Integer rent, Date posted_on) {
        this.owner_id = owner_id;
        this.city = city;
        this.district = district;
        this.advance = advance;
        this.rent = rent;
        this.posted_on = posted_on;
    }

    public String getOwner_id() { return this.owner_id; }
    public String getCity() { return this.city; }
    public String getDistrict() { return this.district; }
    public Integer getAdvance() { return  this.advance; }
    public Integer getRent() { return this.rent; }
    public Date getPosted_on() { return this.posted_on; }

    public Map <String, Object> toMap() {
        HashMap <String, Object> result = new HashMap<>();
        result.put("city", city);
        result.put("district", district);
        result.put("advance", advance);
        result.put("rent", rent);
        result.put("posted_on", posted_on);

        return result;
    }

}

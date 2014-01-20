package com.captechconsulting.facade.v1_0.data;

import com.captechconsulting.facade.v1_0.validators.Numeric;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

public class LocationVO {

    private Long id;

    @NotNull
    @Numeric
    private String locationId;

    @NotNull
    @Past
    private Date timestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

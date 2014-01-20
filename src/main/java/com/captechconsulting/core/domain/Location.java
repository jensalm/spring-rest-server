package com.captechconsulting.core.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String locationId;

    @Temporal(TemporalType.TIMESTAMP)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        return !(id != null ? !id.equals(location.id) : location.id != null) &&
                !(locationId != null ? !locationId.equals(location.locationId) : location.locationId != null) &&
                !(timestamp != null ? !timestamp.equals(location.timestamp) : location.timestamp != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (locationId != null ? locationId.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}

package com.captechconsulting.core.dao;

import com.captechconsulting.core.domain.Location;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class LocationDaoImpl extends AbstractDao<Location> implements LocationDao {

    public LocationDaoImpl() {
        super(Location.class);
    }
}

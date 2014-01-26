package com.captechconsulting.core.service;

import com.captechconsulting.core.dao.LocationDao;
import com.captechconsulting.core.domain.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LocationService {

    @Autowired
    private LocationDao locationDao;
                          
    public Location get(final long id) {
        return locationDao.findById(id);
    }

    public List<Location> list(int page, int size) {
        return locationDao.findAll(page, size);
    }

    public Location store(Location ticket) {
        if (ticket.getId() != null) {
            return locationDao.update(ticket);
        } else {
            return locationDao.create(ticket);
        }
    }

    public void delete(Location ticket) {
        if (ticket.getId() != null) {
            locationDao.delete(ticket);
        } else {
            locationDao.delete(ticket);
        }
    }

}

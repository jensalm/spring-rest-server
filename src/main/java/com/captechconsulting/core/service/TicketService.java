package com.captechconsulting.core.service;

import com.captechconsulting.core.dao.LocationDao;
import com.captechconsulting.core.dao.TicketDao;
import com.captechconsulting.core.domain.Location;
import com.captechconsulting.core.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private LocationDao locationDao;

    public Ticket get(final long id) {
        return ticketDao.findById(id);
    }

    public List<Ticket> listAll(int page, int size) {
        return ticketDao.findAll(page, size);
    }

    public Ticket store(Ticket ticket) {
        if (ticket.getId() != null) {
            return ticketDao.update(ticket);
        } else {
            return ticketDao.create(ticket);
        }
    }

    public void delete(Ticket ticket) {
        if (ticket.getId() != null) {
            ticketDao.delete(ticket);
        } else {
            ticketDao.delete(ticket);
        }
    }

    public Location getLocation(Ticket ticket, Long locationId) {
        Location location = locationDao.findById(locationId);
        if (ticket.getLocations().contains(location)) {
            return location;
        }

        throw new ObjectRetrievalFailureException(Location.class, locationId);
    }

    public void addLocation(Ticket ticket, Location location) {
        ticket.getLocations().add(location);
        store(ticket);
    }

    public void deleteLocation(Ticket ticket, Long locationId) {

        for (Iterator<Location> locationIterator = ticket.getLocations().iterator() ; locationIterator.hasNext(); ) {
            Location location = locationIterator.next();
            if (locationId.equals(location.getId())) {
                locationIterator.remove();
                locationDao.delete(location);
                return;
            }
        }

        throw new ObjectRetrievalFailureException(Location.class, locationId);
    }
}

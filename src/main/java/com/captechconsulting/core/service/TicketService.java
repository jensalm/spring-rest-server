package com.captechconsulting.core.service;

import com.captechconsulting.core.dao.LocationDao;
import com.captechconsulting.core.dao.TicketDao;
import com.captechconsulting.core.domain.Location;
import com.captechconsulting.core.domain.LocationScan;
import com.captechconsulting.core.domain.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        Ticket ticket = ticketDao.findOne(id);
        if (ticket != null) {
            return ticket;
        }
        throw new ObjectRetrievalFailureException(Ticket.class, id);
    }

    public List<Ticket> list(int page, int size) {
        return ticketDao.findAll(new PageRequest(page, size)).getContent();
    }

    public Ticket store(Ticket ticket) {
        return ticketDao.save(ticket);
    }

    public void delete(Ticket ticket) {
        ticketDao.delete(ticket);
    }

    public Location addLocationScan(Ticket ticket, Long locationId) {
        Location location = locationDao.findOne(locationId);
        if (location == null) {
            throw new ObjectRetrievalFailureException(Ticket.class, locationId);
        }

        LocationScan locationScan = new LocationScan();
        locationScan.setLocation(location);
        locationScan.setTimestamp(new Date());
        ticket.getLocationScans().add(locationScan);
        store(ticket);

        return location;
    }

    public void deleteLocationScan(Ticket ticket, Long locationId) {

        for (Iterator<LocationScan> locationIterator = ticket.getLocationScans().iterator() ; locationIterator.hasNext(); ) {
            LocationScan location = locationIterator.next();
            if (locationId.equals(location.getId())) {
                locationIterator.remove();
                return;
            }
        }

        throw new ObjectRetrievalFailureException(Location.class, locationId);
    }
}

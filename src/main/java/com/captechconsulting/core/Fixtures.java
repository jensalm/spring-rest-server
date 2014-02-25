package com.captechconsulting.core;


import com.captechconsulting.core.dao.LocationDao;
import com.captechconsulting.core.dao.TicketDao;
import com.captechconsulting.core.domain.Address;
import com.captechconsulting.core.domain.Location;
import com.captechconsulting.core.domain.LocationScan;
import com.captechconsulting.core.domain.Ticket;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class Fixtures {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private LocationDao locationDao;

    @PostConstruct
    public void run() {
        // Locations
        Location location1 = createLocation("Grocery Store", createAddress("123 Some Street", "Some City", "543 21"));
        locationDao.save(location1);
        Location location2 = createLocation("Other Store", createAddress("123 Middle of Nowhere Street", "Boonies", "890 34"));
        locationDao.save(location2);
        Location location3 = createLocation("Mail Supply", createAddress("45 Nowhere Street", "Lost City", "675 12"));
        locationDao.save(location3);
        Location location4 = createLocation("Sorting Hub", createAddress("321 Other Street", "Some City", "123 89"));
        locationDao.save(location4);

        // Version 1 tickets (without email)
        Ticket ticket1 = createTicket("John", "Doe", null, "ab-123-12345", createAddress("123 Street", "City", "123 45"));
        Ticket ticket2 = createTicket("John", "Doe", null, "ab-123-12345", createAddress("123 Street", "City", "123 45"));

        // Version 2 tickets (with email)
        Ticket ticket3 = createTicket("John", "Doe", "john.doe@email.com", "cd-456-67890", createAddress("123 Street", "City", "123 45"));

        // Adding scans to Location 1
        ticket1.getLocationScans().add(createLocationScan(location1, new LocalDateTime().minusDays(35).toDate()));
        ticket1.getLocationScans().add(createLocationScan(location2, new LocalDateTime().minusDays(30).toDate()));
        ticket1.getLocationScans().add(createLocationScan(location3, new LocalDateTime().minusDays(25).toDate()));

        // Adding scans to Location 2
        ticket2.getLocationScans().add(createLocationScan(location1, new LocalDateTime().minusDays(5).toDate()));
        ticket2.getLocationScans().add(createLocationScan(location2, new LocalDateTime().minusDays(4).toDate()));

        // Adding scans to Location 3
        ticket3.getLocationScans().add(createLocationScan(location1, new LocalDateTime().minusDays(7).toDate()));
        ticket3.getLocationScans().add(createLocationScan(location2, new LocalDateTime().minusDays(3).toDate()));
        ticket3.getLocationScans().add(createLocationScan(location3, new LocalDateTime().minusDays(2).toDate()));
        ticket3.getLocationScans().add(createLocationScan(location4, new LocalDateTime().minusDays(1).toDate()));

        ticketDao.save(ticket1);
        ticketDao.save(ticket2);
        ticketDao.save(ticket3);
    }

    private Location createLocation(String name, Address address) {
        Location location = new Location();
        location.setName(name);
        location.setAddress(address);
        return location;
    }

    private Ticket createTicket(String firstName, String lastName, String email, String packageNumber, Address address) {
        Ticket ticket = new Ticket();
        ticket.setFirstName(firstName);
        ticket.setLastName(lastName);
        ticket.setEmail(email);
        ticket.setPackageNumber(packageNumber);
        ticket.setAddress(address);
        return ticket;
    }

    private Address createAddress(String street, String city, String zip) {
        Address address = new Address();
        address.setStreet(street);
        address.setCity(city);
        address.setZip(zip);
        return address;
    }

    private LocationScan createLocationScan(Location location, Date date) {
        LocationScan locationScan = new LocationScan();
        locationScan.setLocation(location);
        locationScan.setTimestamp(date);
        return locationScan;
    }

}

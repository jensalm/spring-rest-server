package com.captechconsulting.facade.v1_0.controllers;

import com.captechconsulting.core.domain.Location;
import com.captechconsulting.core.domain.LocationScan;
import com.captechconsulting.core.domain.Ticket;
import com.captechconsulting.core.service.LocationService;
import com.captechconsulting.core.service.MappingService;
import com.captechconsulting.core.service.TicketService;
import com.captechconsulting.facade.Versions;
import com.captechconsulting.facade.v1_0.data.LocationScanVO;
import com.captechconsulting.facade.v1_0.data.LocationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RestController
public class LocationFacade {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private MappingService mappingService;

    @RequestMapping(value = "/location/{locationId}", method = RequestMethod.GET, produces = Versions.V1_0, consumes = Versions.V1_0)
    public LocationVO getLocation(@PathVariable Long locationId) {
        Location location = locationService.get(locationId);
        return mappingService.map(location, LocationVO.class);
    }

    @RequestMapping(value = "/ticket/{ticketId}/locations", method = RequestMethod.GET, produces = Versions.V1_0, consumes = Versions.V1_0)
    public List<LocationScanVO> getAllLocations(@PathVariable long ticketId) {
        Ticket ticket = ticketService.get(ticketId);
        List<LocationScan> locations = ticket.getLocationScans();
        return mappingService.map(locations, LocationScanVO.class);
    }

    @RequestMapping(value = "/ticket/{ticketId}/scan/{locationId}", method = RequestMethod.POST, produces = Versions.V1_0, consumes = Versions.V1_0)
    @ResponseStatus(HttpStatus.CREATED)
    public LocationVO addLocationScan(@PathVariable long ticketId, @PathVariable long locationId) {
        Ticket ticket = ticketService.get(ticketId);
        Location location = ticketService.addLocationScan(ticket, locationId);
        return mappingService.map(location, LocationVO.class);
    }

    @RequestMapping(value = "/ticket/{ticketId}/scan/{locationId}", method = RequestMethod.DELETE, produces = Versions.V1_0, consumes = Versions.V1_0)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLocationScan(@PathVariable long ticketId, @PathVariable long locationId) {
        Ticket ticket = ticketService.get(ticketId);
        ticketService.deleteLocationScan(ticket, locationId);
    }

}

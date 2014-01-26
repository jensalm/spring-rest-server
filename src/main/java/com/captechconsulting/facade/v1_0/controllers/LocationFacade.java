package com.captechconsulting.facade.v1_0.controllers;

import com.captechconsulting.core.domain.Location;
import com.captechconsulting.core.domain.Ticket;
import com.captechconsulting.core.service.MappingService;
import com.captechconsulting.core.service.TicketService;
import com.captechconsulting.facade.Versions;
import com.captechconsulting.facade.v1_0.data.LocationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/ticket")
public class LocationFacade {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MappingService mappingService;

    @RequestMapping(value = "/{ticketId}/location", method = RequestMethod.GET, produces = Versions.V1_0, consumes = Versions.V1_0)
    @ResponseStatus(HttpStatus.CREATED)
    public List<LocationVO> getAllLocations(@PathVariable long ticketId) {
        Ticket ticket = ticketService.get(ticketId);
        List<Location> locations = ticket.getLocations();
        return mappingService.map(locations, LocationVO.class);
    }

    @RequestMapping(value = "/{ticketId}/location/{locationId}", method = RequestMethod.GET, produces = Versions.V1_0, consumes = Versions.V1_0)
    @ResponseStatus(HttpStatus.CREATED)
    public LocationVO getLocation(@PathVariable long ticketId, @PathVariable Long locationId) {
        Ticket ticket = ticketService.get(ticketId);
        Location location = ticketService.getLocation(ticket, locationId);
        return mappingService.map(location, LocationVO.class);
    }

    @RequestMapping(value = "/{ticketId}/location", method = RequestMethod.POST, produces = Versions.V1_0, consumes = Versions.V1_0)
    @ResponseStatus(HttpStatus.CREATED)
    public LocationVO addLocation(@PathVariable long ticketId, @Valid @RequestBody LocationVO location) {
        Ticket ticket = ticketService.get(ticketId);
        Location mappedLocation = mappingService.map(location, Location.class);
        ticketService.addLocation(ticket, mappedLocation);
        return mappingService.map(mappedLocation, LocationVO.class);
    }

    @RequestMapping(value = "/{ticketId}/location/{locationId}", method = RequestMethod.DELETE, produces = Versions.V1_0, consumes = Versions.V1_0)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable long ticketId, @PathVariable long locationId) {
        Ticket ticket = ticketService.get(ticketId);
        ticketService.deleteLocation(ticket, locationId);
    }

}

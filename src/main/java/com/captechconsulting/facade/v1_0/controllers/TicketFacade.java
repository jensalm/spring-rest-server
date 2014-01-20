package com.captechconsulting.facade.v1_0.controllers;

import com.captechconsulting.core.domain.Ticket;
import com.captechconsulting.core.service.MappingService;
import com.captechconsulting.facade.Versions;
import com.captechconsulting.core.service.TicketService;
import com.captechconsulting.facade.v1_0.data.TicketVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Transactional
@RestController
@RequestMapping("/ticket")
public class TicketFacade {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MappingService mappingService;

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.GET, produces = Versions.V1_0, consumes = Versions.V1_0)
    public TicketVO getUser(@PathVariable long ticketId) {
        Ticket ticket = ticketService.get(ticketId);
        return mappingService.map(ticket, TicketVO.class);
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.PUT, produces = Versions.V1_0, consumes = Versions.V1_0)
    public TicketVO update(@PathVariable long ticketId, @Valid @RequestBody TicketVO ticket) {
        Ticket previouslyPersisted = ticketService.get(ticketId);
        mappingService.map(ticket, previouslyPersisted);
        Ticket persisted = ticketService.store(previouslyPersisted);
        return mappingService.map(persisted, TicketVO.class);
    }

    @RequestMapping(method = RequestMethod.POST, produces = Versions.V1_0, consumes = Versions.V1_0)
    @ResponseStatus(HttpStatus.CREATED)
    public TicketVO create(@Valid @RequestBody TicketVO ticket) {
        Ticket mappedTicket = mappingService.map(ticket, Ticket.class);
        Ticket persisted = ticketService.store(mappedTicket);
        return mappingService.map(persisted, TicketVO.class);
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.DELETE, produces = Versions.V1_0, consumes = Versions.V1_0)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public TicketVO remove(@PathVariable long ticketId) {
        Ticket ticket = ticketService.get(ticketId);
        ticketService.delete(ticket);
        return null;
    }

}

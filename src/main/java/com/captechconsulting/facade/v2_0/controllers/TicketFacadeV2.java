package com.captechconsulting.facade.v2_0.controllers;

import com.captechconsulting.core.domain.Ticket;
import com.captechconsulting.core.service.MappingService;
import com.captechconsulting.core.service.TicketService;
import com.captechconsulting.facade.Versions;
import com.captechconsulting.facade.v2_0.data.TicketVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Transactional
@RestController
@RequestMapping(value = "/ticket", produces = Versions.V2_0, consumes = Versions.V2_0)
public class TicketFacadeV2 {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MappingService mappingService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<TicketVO> list(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "10") int size) {
        List<Ticket> tickets = ticketService.list(page, size);
        return mappingService.map(tickets, TicketVO.class);
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.GET)
    public TicketVO read(@PathVariable long ticketId) {
        Ticket ticket = ticketService.get(ticketId);
        return mappingService.map(ticket, TicketVO.class);
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.PATCH)
    public TicketVO update(@PathVariable long ticketId, @Valid @RequestBody TicketVO ticket) {
        Ticket previouslyPersisted = ticketService.get(ticketId);
        mappingService.map(ticket, previouslyPersisted);
        Ticket persisted = ticketService.store(previouslyPersisted);
        return mappingService.map(persisted, TicketVO.class);
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.PUT)
    public TicketVO replace(@PathVariable long ticketId, @Valid @RequestBody TicketVO ticket) {
        Ticket previouslyPersisted = ticketService.get(ticketId);
        Ticket newTicket = mappingService.map(ticket, Ticket.class);
        newTicket.setId(previouslyPersisted.getId());
        Ticket persisted = ticketService.store(newTicket);
        return mappingService.map(persisted, TicketVO.class);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TicketVO create(@Valid @RequestBody TicketVO ticket) {
        Ticket mappedTicket = mappingService.map(ticket, Ticket.class);
        Ticket persisted = ticketService.store(mappedTicket);
        return mappingService.map(persisted, TicketVO.class);
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable long ticketId) {
        Ticket ticket = ticketService.get(ticketId);
        ticketService.delete(ticket);
    }

}

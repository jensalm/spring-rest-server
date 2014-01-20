package com.captechconsulting.core.dao;

import com.captechconsulting.core.domain.Ticket;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class TicketDaoImpl extends AbstractDao<Ticket> implements TicketDao {

    public TicketDaoImpl() {
        super(Ticket.class);
    }
}

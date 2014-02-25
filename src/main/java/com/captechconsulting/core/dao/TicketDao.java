package com.captechconsulting.core.dao;

import com.captechconsulting.core.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TicketDao extends JpaRepository<Ticket, Long> {

}

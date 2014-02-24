package com.captechconsulting.core.dao;

import com.captechconsulting.core.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LocationDao extends JpaRepository<Location, Long> {
}

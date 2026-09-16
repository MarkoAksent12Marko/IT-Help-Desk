package com.example.ithelpdesk.repository;

import com.example.ithelpdesk.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUsernameOrderByIdDesc(String username);
}

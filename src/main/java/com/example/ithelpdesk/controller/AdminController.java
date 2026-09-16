package com.example.ithelpdesk.controller;

import com.example.ithelpdesk.model.Ticket;
import com.example.ithelpdesk.repository.TicketRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final TicketRepository ticketRepository;

    public AdminController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping
    public String adminDashboard(Model model) {

        model.addAttribute(
                "tickets",
                ticketRepository.findAll()
        );

        return "admin";
    }

    @PostMapping("/tickets/{id}/status")
    public String updateStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        Ticket ticket = ticketRepository
                .findById(id)
                .orElseThrow();

        ticket.setStatus(status);

        ticketRepository.save(ticket);

        return "redirect:/admin";
    }
}

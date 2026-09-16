package com.example.ithelpdesk.controller;

import com.example.ithelpdesk.model.Ticket;
import com.example.ithelpdesk.repository.TicketRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    private final TicketRepository ticketRepository;

    public HomeController(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {

        String username = authentication.getName();

        model.addAttribute("username", username);

        model.addAttribute(
                "tickets",
                ticketRepository.findByUsernameOrderByIdDesc(username)
        );

        return "index";
    }

    @PostMapping("/tickets")
    public String createTicket(
            @RequestParam String employeeName,
            @RequestParam String department,
            @RequestParam String issue,
            @RequestParam String priority,
            Authentication authentication) {

        Ticket ticket = new Ticket();

        ticket.setEmployeeName(employeeName);
        ticket.setDepartment(department);
        ticket.setIssue(issue);
        ticket.setPriority(priority);
        ticket.setStatus("OPEN");

        ticket.setUsername(authentication.getName());

        ticketRepository.save(ticket);

        return "redirect:/";
    }

    @GetMapping("/about")
    public String about(Model model, Authentication authentication) {

        boolean isAdmin = authentication
                .getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("isAdmin", isAdmin);

        return "about";
    }
}
package com.project.din.ticketbooking.controller;

import com.project.din.ticketbooking.model.Ticket;
import com.project.din.ticketbooking.model.TicketInfo;
import com.project.din.ticketbooking.model.UpdTicket;
import com.project.din.ticketbooking.service.TicketService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("create")
    public ResponseEntity<Ticket> createReservation(@RequestBody TicketInfo info) {
        Ticket ticket = ticketService.createTicket(info);
        if(ticket == null) {
            return ResponseEntity.internalServerError().build();
        } else
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ticket);
    }

    @PutMapping("update")
    public ResponseEntity<Ticket> updateReservation(@RequestBody UpdTicket info) {
        Ticket ticket = ticketService.updateTicket(info);
        if(ticket == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else if (ObjectUtils.isEmpty(ticket)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ticket);
    }

    @GetMapping("details")
    public ResponseEntity<List<Ticket>> getTicket(@RequestParam String email) {
        List<Ticket> ticket = ticketService.getTicket(email);
        if(ticket.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ticket);
    }

    @GetMapping("coach")
    public ResponseEntity<List<Ticket>> getTicketsByCoach(@RequestParam String date, @RequestParam String coach) {
        List<Ticket> ticket = ticketService.showAllByCoachAndDate(date, coach);
        if(ticket.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ticket);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteTicket(@RequestParam("email") String email) {
        Boolean result = ticketService.deleteTicket(email);
        if(result) {
            return ResponseEntity.status(HttpStatus.OK).body("Delete successful");
        } else
            return ResponseEntity.status(HttpStatus.OK).body("No record to delete");
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteTicketById(@PathParam("id") Long id) {
        Boolean result = ticketService.deleteTicketById(id);
        if(result) {
            return ResponseEntity.status(HttpStatus.OK).body("Delete successful");
        } else
            return ResponseEntity.status(HttpStatus.OK).body("No record to delete");

    }
}

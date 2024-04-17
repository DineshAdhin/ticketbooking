package com.project.din.ticketbooking.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Ticket {

    private Long id;
    private String source;
    private String destination;
    private Float cost;
    private LocalDate date;
    private Customer customer;
    private Seat seat;
}

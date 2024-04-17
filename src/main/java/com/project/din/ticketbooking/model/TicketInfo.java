package com.project.din.ticketbooking.model;

import lombok.Data;

@Data
public class TicketInfo {
    private String source;
    private String destination;
    private Float cost;
    private String firstName;
    private String lastName;
    private String email;
}

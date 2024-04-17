package com.project.din.ticketbooking.model;

import lombok.Data;

@Data
public class UpdTicket {
    private Long ticketId;
    private String coach;
    private Integer seatNumber;
}

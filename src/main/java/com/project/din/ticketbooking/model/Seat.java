package com.project.din.ticketbooking.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Seat {

    private Long id;
    private LocalDate date;
    private String coach;
    private Integer number;
}

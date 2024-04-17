package com.project.din.ticketbooking.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name="ticket")
@Data
public class TicketDao {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name="source", nullable = false)
    private String source;
    @Column(name="destination", nullable = false)
    private String destination;
    @Column(name="cost", nullable = false)
    private Float cost;
    @Column(name="date", nullable = false)
    private LocalDate date;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerDao customer;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    private SeatDao seat;
}

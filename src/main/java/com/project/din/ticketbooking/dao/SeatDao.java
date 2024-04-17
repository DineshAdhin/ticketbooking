package com.project.din.ticketbooking.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name="seat")
@Data
public class SeatDao {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name="date", nullable = false)
    private LocalDate date;

    @Column(name="coach", nullable = false, length = 1)
    private String coach;

    @Column(name="number", nullable = false)
    @Min(1)
    @Max(50)
    private Integer number;
}

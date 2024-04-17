package com.project.din.ticketbooking.dao;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="customer")
@Data
public class CustomerDao {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(name="first_name", nullable = false)
    private String firstName;
    @Column(name="last_name", nullable = false)
    private String lastName;
    @Column(name="email", nullable = false, unique = true)
    private String email;
}

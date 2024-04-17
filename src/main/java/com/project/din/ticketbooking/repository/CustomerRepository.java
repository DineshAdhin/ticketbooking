package com.project.din.ticketbooking.repository;

import com.project.din.ticketbooking.dao.CustomerDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerDao, Long> {

    public Optional<CustomerDao> findByFirstNameIgnoreCase(String firstName);

    public Optional<CustomerDao> findByEmailIgnoreCase(String email);
}

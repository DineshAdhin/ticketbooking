package com.project.din.ticketbooking.repository;

import com.project.din.ticketbooking.dao.TicketDao;
import com.project.din.ticketbooking.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketDao, Long> {

    public Optional<TicketDao> findByDate(LocalDate date);

    public List<TicketDao> findAllByCustomer_EmailIgnoreCase(String email);

    public List<TicketDao> findAllByDateAndSeat_CoachIgnoreCase(LocalDate date, String coach);

    public List<TicketDao> findAllBySeat_CoachIgnoreCaseAndDate(String coach, LocalDate date);

    public Optional<TicketDao> findTopBySeat_CoachIgnoreCaseAndDateOrderBySeat_NumberDesc(String coach, LocalDate date);
}

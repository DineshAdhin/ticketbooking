package com.project.din.ticketbooking.repository;

import com.project.din.ticketbooking.dao.SeatDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<SeatDao, Long> {
    public Optional<SeatDao> findByCoachIgnoreCaseAndDateAndNumber(String coach, LocalDate date, Integer number);

    public List<SeatDao> findAllByDateAndCoach(LocalDate date, String coach);
}

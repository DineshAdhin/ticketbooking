package com.project.din.ticketbooking.service;

import com.project.din.ticketbooking.dao.CustomerDao;
import com.project.din.ticketbooking.converter.CustomerMapper;
import com.project.din.ticketbooking.converter.SeatMapper;
import com.project.din.ticketbooking.converter.TicketMapper;
import com.project.din.ticketbooking.dao.SeatDao;
import com.project.din.ticketbooking.dao.TicketDao;
import com.project.din.ticketbooking.model.Ticket;
import com.project.din.ticketbooking.model.TicketInfo;
import com.project.din.ticketbooking.model.UpdTicket;
import com.project.din.ticketbooking.repository.CustomerRepository;
import com.project.din.ticketbooking.repository.SeatRepository;
import com.project.din.ticketbooking.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketMapper ticketMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private SeatMapper seatMapper;

    private Integer findMissingNumber(List<Integer> numbers) {
        int missingNumber = 1;
        Collections.sort(numbers);
        for (int i = 1; i < 51; i++) {
            if (numbers.size() >= i && numbers.get(i-1) != i) {
                missingNumber = i;
                break;
            } else if(numbers.size() == i && numbers.get(i-1) == i) {
                missingNumber = i + 1;
                break;
            }
        }
        return missingNumber;
    }

    public Ticket createTicket(TicketInfo info) {
        Ticket result;
        TicketDao response = null;
        LocalDate date = LocalDate.now();
        List<TicketDao> aTickets = ticketRepository.findAllBySeat_CoachIgnoreCaseAndDate("A", date);
        if(aTickets.size() == 50) {
            List<TicketDao> bTickets = ticketRepository.findAllBySeat_CoachIgnoreCaseAndDate("B", date);
            if(bTickets.size() == 50) {
                return null;
            } else if(bTickets.size() > 0 ) {
                List<Integer> numbers = seatRepository.findAllByDateAndCoach(date, "B").stream().map(i->i.getNumber()).collect(Collectors.toList());
                Integer seatNumber = findMissingNumber(numbers);
                response = createTicketDao(info, date, seatNumber, "B");
            } else {
                response = createTicketDao(info, date, 1, "B");
            }
        } else if(aTickets.size() > 0 ) {
            List<Integer> numbers = seatRepository.findAllByDateAndCoach(date, "A").stream().map(i->i.getNumber()).collect(Collectors.toList());
            Integer seatNumber = findMissingNumber(numbers);
            response = createTicketDao(info, date, seatNumber, "A");
        }
        else {
            response = createTicketDao(info, date, 1, "A");
        }
        result = ticketMapper.entityToModel(response);
        return result;
    }



    private TicketDao createTicketDao(TicketInfo info, LocalDate date, Integer seatNumber, String coach){
        Optional<CustomerDao> tmpCustomer = customerRepository.findByEmailIgnoreCase(info.getEmail());
        CustomerDao customer = new CustomerDao();
        if(tmpCustomer.isEmpty()) {
            customer.setEmail(info.getEmail());
            customer.setFirstName(info.getFirstName());
            customer.setLastName(info.getLastName());
        } else {
            customer = tmpCustomer.get();
        }

        SeatDao seat = new SeatDao();
        seat.setCoach(coach);
        seat.setNumber(seatNumber);
        seat.setDate(date);

        TicketDao ticket = new TicketDao();
        ticket.setDate(date);
        ticket.setCost(info.getCost());
        ticket.setCustomer(customer);
        ticket.setSeat(seat);
        ticket.setDestination(info.getDestination());
        ticket.setSource(info.getSource());

        TicketDao response = ticketRepository.saveAndFlush(ticket);
        return response;
    }

    public List<Ticket> getTicket(String email){
        List<TicketDao> result = ticketRepository.findAllByCustomer_EmailIgnoreCase(email);
        if(result.isEmpty()) {
            return new ArrayList<>();
        } else {
            List<Ticket> ticketList = ticketMapper.entityToModelList(result);
            return ticketList;
        }
    }

    public Boolean deleteTicket(String email){
        List<TicketDao> result = ticketRepository.findAllByCustomer_EmailIgnoreCase(email);
        if(result.isEmpty()) {
            return false;
        } else {
            List<Long> ids = result.stream().parallel().map(i->i.getId()).collect(Collectors.toList());
            ticketRepository.deleteAllByIdInBatch(ids);
            return true;
        }
    }

    public Boolean deleteTicketById(Long id){
        Optional<TicketDao> result = ticketRepository.findById(id);
        if(result.isEmpty()) {
            return false;
        } else {
            ticketRepository.deleteById(id);
            return true;
        }
    }

    public Ticket updateTicket(UpdTicket info) {
        Ticket result;
        Optional<TicketDao> response = ticketRepository.findById(info.getTicketId());
        if(response.isPresent()) {
            TicketDao tmp = response.get();
            SeatDao seatDao = tmp.getSeat();
            Optional<SeatDao> optSeatDao = seatRepository.findByCoachIgnoreCaseAndDateAndNumber(info.getCoach(), response.get().getDate(), info.getSeatNumber());
            if(optSeatDao.isEmpty()){
                seatDao.setCoach(info.getCoach());
                seatDao.setNumber(info.getSeatNumber());
                seatRepository.saveAndFlush(seatDao);
                response = ticketRepository.findById(info.getTicketId());
                result = ticketMapper.entityToModel(response.get());
                return result;
            } else {
                return null;
            }
        } else {
            return new Ticket();
        }
    }

    public List<Ticket> showAllByCoachAndDate(String date, String coach) {
        List<Ticket> result;
        List<TicketDao> response = null;
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate dt = LocalDate.parse(date, dtf);
        response = ticketRepository.findAllByDateAndSeat_CoachIgnoreCase(dt, coach);
        result = ticketMapper.entityToModelList(response);
        return result;
    }
}

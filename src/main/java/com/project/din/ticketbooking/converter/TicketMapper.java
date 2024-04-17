package com.project.din.ticketbooking.converter;

import com.project.din.ticketbooking.dao.TicketDao;
import com.project.din.ticketbooking.model.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TicketMapper {

    @Autowired
    protected CustomerMapper customerMapper;

    @Autowired
    protected SeatMapper seatMapper;

    @Mappings({
            @Mapping(target = "seat", expression="java(seatMapper.entityToModel(entity.getSeat()))"),
            @Mapping(target = "customer", expression="java(customerMapper.entityToModel(entity.getCustomer()))")
    })
    public abstract Ticket entityToModel(TicketDao entity);

    @Mappings({
            @Mapping(target = "seat", expression="java(seatMapper.entityToModel(entity.getSeat()))"),
            @Mapping(target = "customer", expression="java(customerMapper.entityToModel(entity.getCustomer()))")
    })
    public abstract List<Ticket> entityToModelList(List<TicketDao> entity);
    public abstract TicketDao modelToEntity(Ticket model);

}

package com.project.din.ticketbooking.converter;

import com.project.din.ticketbooking.dao.SeatDao;
import com.project.din.ticketbooking.model.Seat;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class SeatMapper {

    public abstract Seat entityToModel(SeatDao entity);
    public abstract SeatDao modelToEntity(Seat model);

}

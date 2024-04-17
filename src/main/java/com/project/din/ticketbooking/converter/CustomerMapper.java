package com.project.din.ticketbooking.converter;

import com.project.din.ticketbooking.dao.CustomerDao;
import com.project.din.ticketbooking.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper {

    public abstract Customer entityToModel(CustomerDao entity);
    public abstract CustomerDao modelToEntity(Customer model);

}

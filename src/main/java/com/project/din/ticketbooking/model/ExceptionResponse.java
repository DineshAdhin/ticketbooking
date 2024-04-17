package com.project.din.ticketbooking.model;

import lombok.Data;

@Data
public class ExceptionResponse {

    private String errorMessage;
    private String requestURI;
}

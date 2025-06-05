package com.example.cto.dto;

import jakarta.validation.constraints.NotBlank;

// data for create service request
public record CreateRequestDTO(

    @NotBlank(message = "clientName is required")
    String clientName,

    @NotBlank(message = "carModel is required")
    String carModel,

    @NotBlank(message = "description is required")
    String description
) {}

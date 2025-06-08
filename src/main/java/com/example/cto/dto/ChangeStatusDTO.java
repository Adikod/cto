package com.example.cto.dto;

import com.example.cto.domain.RequestStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// data for change request status
public record ChangeStatusDTO(

    @NotNull(message = "id is required")
    Long id,

    @NotNull(message = "newStatus is required")
    RequestStatus newStatus,

    @NotBlank(message = "changedBy is required")
    String changedBy,

    @NotBlank(message = "reason is required")
    String reason
) {}

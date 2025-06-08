package com.example.cto.kafka;

import com.example.cto.domain.RequestStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// for kafka send
@JsonIgnoreProperties(ignoreUnknown = true)
public record StatusChangeEvent(
    Long requestId,
    RequestStatus newStatus,
    String changedBy,
    String reason
) {}

package com.example.cto.kafka;

import com.example.cto.domain.RequestStatus;

// for kafka send
public record StatusChangeEvent(
    Long requestId,
    RequestStatus newStatus,
    String changedBy,
    String reason
) {}

package com.example.cto.domain;

// Possible statuses of service request
public enum RequestStatus {
    CREATED,     // Request created
    ACCEPTED,    // Request accepted
    DIAGNOSIS,   // Work scope defined
    REPAIRING,   // Repair in progress
    COMPLETED    // Work completed and client notified
}

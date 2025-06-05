package com.example.cto.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

// single status change in a request
@Data //auto gen of setter getters...
@Entity
public class StatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private ServiceRequest request;

    // When status was changed
    private LocalDateTime timestamp;

    // Who changed the status
    private String changedBy;

    // Why status was changed
    private String reason;

    // What status was set
    @Enumerated(EnumType.STRING)
    private RequestStatus newStatus;
}

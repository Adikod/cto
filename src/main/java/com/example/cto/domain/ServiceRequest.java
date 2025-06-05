package com.example.cto.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Data //auto gen of setter getters...
@Entity
public class ServiceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // client name
    private String clientName;

    // car model
    private String carModel;

    // desc of the issue
    private String description;

    // current status of the request
    @Enumerated(EnumType.STRING)
    private RequestStatus status;

    // history of status change
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<StatusHistory> history = new ArrayList<>();

}

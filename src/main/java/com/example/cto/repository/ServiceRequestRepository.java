package com.example.cto.repository;

import com.example.cto.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// JPA repository for ServiceRequest entity
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {

    // find all requests by client name
    List<ServiceRequest> findByClientName(String clientName);

    // find all requests by current status
    List<ServiceRequest> findByStatus(RequestStatus status);
}

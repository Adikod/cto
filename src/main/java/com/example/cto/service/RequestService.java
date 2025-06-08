package com.example.cto.service;

import com.example.cto.domain.*;
import com.example.cto.kafka.StatusChangeEvent;
import com.example.cto.kafka.StatusKafkaProducer;
import com.example.cto.repository.ServiceRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// logic for service requests
@Slf4j
@Service
@RequiredArgsConstructor        // for generate constructors for final fields
public class RequestService {

    private final ServiceRequestRepository repository;
    private final StatusKafkaProducer kafkaProducer;

    public void sendStatusChangeKafka(Long id, RequestStatus status, String by, String reason) {
        kafkaProducer.sendStatusChange(new StatusChangeEvent(id, status, by, reason));
    }

    // create a new service request
    public ServiceRequest createRequest(String clientName, String carModel, String description) {
        ServiceRequest request = ServiceRequest.builder()
                .clientName(clientName)
                .carModel(carModel)
                .description(description)
                .status(RequestStatus.CREATED)
                .build();
        log.info("Creating request for client={} car={}", clientName, carModel);

        return repository.save(request);
    }

    // update request status
    @Transactional
    public void changeStatus(Long requestId, RequestStatus newStatus, String changedBy, String reason) {

        ServiceRequest request = repository.findById(requestId)// - throws exception if request not found
                .orElseThrow(() -> new IllegalArgumentException("Request not found: id=" + requestId));


        StatusHistory history = StatusHistory.builder()
                .request(request)
                .timestamp(LocalDateTime.now())
                .changedBy(changedBy)
                .reason(reason)
                .prevStatus(request.getStatus())
                .newStatus(newStatus)
                .build();

        request.setStatus(newStatus);
        request.getHistory().add(history);

        // If status is set to COMPLETED, mock the sms by log
        if (newStatus == RequestStatus.COMPLETED) {
            log.info("[TIPA SMS] Notify client {}: Your request is completed.", request.getClientName());
        }
        log.info("Changing status to {} by {} with reason: {}", newStatus, changedBy, reason);


        repository.save(request);
    }


    // gets all requests by client name
    public List<ServiceRequest> getByClient(String clientName) {
        return repository.findByClientName(clientName);
    }

    // gets all requests by input status
    public List<ServiceRequest> getByStatus(RequestStatus status) {
        return repository.findByStatus(status);
    }
}

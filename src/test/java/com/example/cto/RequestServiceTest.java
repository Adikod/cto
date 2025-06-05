package com.example.cto;

import com.example.cto.domain.*;
import com.example.cto.repository.ServiceRequestRepository;
import com.example.cto.service.RequestService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RequestServiceTest {

    @Autowired
    RequestService service;

    @Autowired
    ServiceRequestRepository repository;

    @Test
    @Transactional
    void shouldCreateRequest() {
        var request = service.createRequest("Adiqueue", "4epirka", "Engine knock");

        assertNotNull(request.getId());
        assertEquals(RequestStatus.CREATED, request.getStatus());
        assertEquals("4epirka", request.getCarModel());
        assertTrue(repository.findByClientName("Adiqueue").contains(request));
    }

    @Test
    @Transactional
    void shouldChangeStatusAndRecordHistory() {
        var request = service.createRequest("Adiqueue", "4epirka", "Engine knock");

        var updated = service.changeStatus(
                request.getId(),
                RequestStatus.ACCEPTED,
                "main4el",
                "init review"
        );

        assertEquals(RequestStatus.ACCEPTED, updated.getStatus());
        assertEquals(1, updated.getHistory().size());
        var history = updated.getHistory().getFirst();

        assertEquals("main4el", history.getChangedBy());
        assertEquals("init review", history.getReason());
        assertEquals(RequestStatus.ACCEPTED, history.getNewStatus());
        assertNotNull(history.getTimestamp());
    }
}

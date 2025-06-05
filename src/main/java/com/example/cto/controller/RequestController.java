package com.example.cto.controller;

import com.example.cto.domain.*;
import com.example.cto.dto.*;
import com.example.cto.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static com.example.cto.api.ApiPaths.BASE_V1;

// REST CRUD endpoints
@RestController
@RequestMapping(BASE_V1 + "/requests")
@RequiredArgsConstructor
@Validated
public class RequestController {

    private final RequestService requestService;

    // create new request
    @PostMapping
    public ResponseEntity<ServiceRequest> create(@RequestBody @Valid CreateRequestDTO dto) {
        ServiceRequest created = requestService.createRequest(dto.clientName(), dto.carModel(), dto.description());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // update request status
    @PostMapping("/{id}/status")
    public ResponseEntity<ServiceRequest> changeStatus(@PathVariable Long id, @RequestBody @Valid ChangeStatusDTO dto) {
        ServiceRequest updated = requestService.changeStatus(id, dto.newStatus(), dto.changedBy(), dto.reason());
        return ResponseEntity.ok(updated);
    }

    // gets requests by client name
    @GetMapping("/by-client")
    public List<ServiceRequest> byClient(@RequestParam String name) {
        return requestService.getByClient(name);
    }

    // gets requests by status
    @GetMapping("/by-status")
    public List<ServiceRequest> byStatus(@RequestParam RequestStatus status) {
        return requestService.getByStatus(status);
    }
}

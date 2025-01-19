package com.holiday.request.controllers;

import com.holiday.request.dto.request.CreateLeaveRequestDTO;
import com.holiday.request.model.LeaveRequest;
import com.holiday.request.service.LeaveRequestService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/leave-request/")
@AllArgsConstructor
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    @PostMapping("/create")
    public ResponseEntity<?> addLeaveRequest(@RequestBody CreateLeaveRequestDTO requestDTO) {
        LeaveRequest createdLeaveRequest = leaveRequestService.create(requestDTO);
        return ResponseEntity.ok(createdLeaveRequest);
    }
}

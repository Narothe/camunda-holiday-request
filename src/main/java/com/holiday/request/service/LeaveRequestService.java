package com.holiday.request.service;

import com.holiday.request.dto.request.CreateLeaveRequestDTO;
import com.holiday.request.model.Employee;
import com.holiday.request.model.LeaveRequest;
import com.holiday.request.repository.EmployeeRepository;
import com.holiday.request.repository.LeaveRequestRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveRequest create(CreateLeaveRequestDTO requestDTO) {
        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .employee(employee)
                .startDate(requestDTO.getStartDate())
                .endDate(requestDTO.getEndDate())
                .status(requestDTO.getStatus())
                .build();

        // Zapisz zgłoszenie w bazie danych
        return leaveRequestRepository.save(leaveRequest);
    }
}
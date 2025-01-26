package com.holiday.request.service;

import com.holiday.request.dto.model.LeaveRequestDTO;
import com.holiday.request.dto.request.CreateLeaveRequestDTO;
import com.holiday.request.enums.LeaveStatus;
import com.holiday.request.model.Employee;
import com.holiday.request.model.LeaveRequest;
import com.holiday.request.repository.EmployeeRepository;
import com.holiday.request.repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EmployeeRepository employeeRepository;

    public LeaveRequest create(CreateLeaveRequestDTO requestDTO) {
        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        if (requestDTO.getEndDate().isBefore(requestDTO.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        Date startDate = Date.from(requestDTO.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(requestDTO.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .employee(employee)
                .startDate(startDate)
                .endDate(endDate)
                .status(LeaveStatus.PENDING)
                .build();

        return leaveRequestRepository.save(leaveRequest);
    }

    public List<LeaveRequestDTO> getAllLeaveRequests() {
        return leaveRequestRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LeaveRequestDTO> getLeaveRequestsByStatus(String status) {
        LeaveStatus leaveStatus;
        try {
            leaveStatus = LeaveStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        return leaveRequestRepository.findAll().stream()
                .filter(leaveRequest -> leaveRequest.getStatus() == leaveStatus)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private LeaveRequestDTO convertToDTO(LeaveRequest leaveRequest) {
        return LeaveRequestDTO.builder()
                .id(leaveRequest.getId())
                .employeeId(leaveRequest.getEmployee().getId())
                .startDate(leaveRequest.getStartDate())
                .endDate(leaveRequest.getEndDate())
                .status(leaveRequest.getStatus())
                .build();
    }

    public LeaveRequestDTO updateLeaveRequestStatus(int id, String status) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found with ID: " + id));

        LeaveStatus leaveStatus;
        try {
            leaveStatus = LeaveStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        leaveRequest.setStatus(leaveStatus);
        LeaveRequest updatedLeaveRequest = leaveRequestRepository.save(leaveRequest);

        return convertToDTO(updatedLeaveRequest);
    }

}

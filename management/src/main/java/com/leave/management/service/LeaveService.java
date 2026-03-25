package com.leave.management.service;

import com.leave.management.model.Leave;
import com.leave.management.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    public Leave applyLeave(Leave leave) {

        LocalDate today = LocalDate.now();

        if (leave.getStartDate().isBefore(today)) {
            throw new RuntimeException("Past dates not allowed");
        }

        if (leave.getEndDate().isBefore(leave.getStartDate())) {
            throw new RuntimeException("Invalid date range");
        }

        int days = (int) ChronoUnit.DAYS.between(
                leave.getStartDate(), leave.getEndDate()) + 1;

        leave.setNoOfDays(days);

        Integer used = leaveRepository.getTotalApprovedLeaves(leave.getEmpId());
        if (used == null) used = 0;

        if (used + days > 9) {
            leave.setStatus("REJECTED");
        } else {
            leave.setStatus("PENDING");
        }

        leave.setAppliedAt(LocalDateTime.now());

        return leaveRepository.save(leave);
    }

    public Leave approveLeave(Long id, Long managerId) {

        Leave leave = leaveRepository.findById(id).orElseThrow();

        if (leave.getEmpId().equals(managerId)) {
            throw new RuntimeException("Cannot approve own leave");
        }

        leave.setStatus("APPROVED");
        leave.setApprovedBy(managerId);

        return leaveRepository.save(leave);
    }

    public Leave rejectLeave(Long id, Long managerId, String comment) {

        Leave leave = leaveRepository.findById(id).orElseThrow();

        leave.setStatus("REJECTED");
        leave.setApprovedBy(managerId);
        leave.setManagerComment(comment);

        return leaveRepository.save(leave);
    }
}
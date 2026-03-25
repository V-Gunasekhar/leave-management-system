package com.leave.management.repository;

import com.leave.management.model.Leave;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {

    @Query("SELECT SUM(l.noOfDays) FROM Leave l WHERE l.empId = :empId AND l.status='APPROVED'")
    Integer getTotalApprovedLeaves(@Param("empId") Long empId);

    List<Leave> findByEmpId(Long empId);

    List<Leave> findByStatus(String status);
}
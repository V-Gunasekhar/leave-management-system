package com.leave.management.controller;

import com.leave.management.model.Leave;
import com.leave.management.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @PostMapping
    public Leave applyLeave(@RequestBody Leave leave) {
        return leaveService.applyLeave(leave);
    }

    @PutMapping("/{id}/approve")
    public Leave approve(@PathVariable Long id,
                         @RequestParam Long managerId) {
        return leaveService.approveLeave(id, managerId);
    }

    @PutMapping("/{id}/reject")
    public Leave reject(@PathVariable Long id,
                        @RequestParam Long managerId,
                        @RequestParam String comment) {
        return leaveService.rejectLeave(id, managerId, comment);
    }
}
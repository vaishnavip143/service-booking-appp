package com.vaishnavi.servicebook.controller;

import com.vaishnavi.servicebook.dto.ApiResponse;
import com.vaishnavi.servicebook.userentity.WorkingHour;
import com.vaishnavi.servicebook.service.WorkingHourService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/working-hours")
public class WorkingHourController {

    private final WorkingHourService workingHourService;

    public WorkingHourController(WorkingHourService workingHourService) {
        this.workingHourService = workingHourService;
    }

    // ✅ POST — Add new working hour for a provider
    @PostMapping
    public ResponseEntity<ApiResponse<WorkingHour>> createWorkingHour(@RequestBody WorkingHour workingHour) {
        return ResponseEntity.ok(ApiResponse.success(workingHourService.saveWorkingHour(workingHour),
                "Working hour added successfully"));
    }

    // ✅ GET — Get all working hours
    @GetMapping
    public ResponseEntity<ApiResponse<List<WorkingHour>>> getAllWorkingHours() {
        return ResponseEntity
                .ok(ApiResponse.success(workingHourService.getAllWorkingHours(), "All working hours fetched"));
    }

    // ✅ GET — Get working hours of one provider
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<ApiResponse<List<WorkingHour>>> getWorkingHoursByProvider(@PathVariable Long providerId) {
        return ResponseEntity.ok(
                ApiResponse.success(workingHourService.getWorkingHoursByProvider(providerId), "Working hours fetched"));
    }
}

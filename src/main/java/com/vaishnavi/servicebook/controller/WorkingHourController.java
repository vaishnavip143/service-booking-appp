package com.vaishnavi.servicebook.controller;

import com.vaishnavi.servicebook.Userentity.WorkingHour;
import com.vaishnavi.servicebook.Service.WorkingHourService;
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
    public WorkingHour createWorkingHour(@RequestBody WorkingHour workingHour) {
        return workingHourService.saveWorkingHour(workingHour);
    }

    // ✅ GET — Get all working hours
    @GetMapping
    public List<WorkingHour> getAllWorkingHours() {
        return workingHourService.getAllWorkingHours();
    }

    // ✅ GET — Get working hours of one provider
    @GetMapping("/provider/{providerId}")
    public List<WorkingHour> getWorkingHoursByProvider(@PathVariable Long providerId) {
        return workingHourService.getWorkingHoursByProvider(providerId);
    }
}

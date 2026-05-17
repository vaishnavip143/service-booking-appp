package com.vaishnavi.servicebook.controller;

import com.vaishnavi.servicebook.dto.ApiResponse;
import com.vaishnavi.servicebook.dto.WorkingScheduleDto;
import com.vaishnavi.servicebook.service.ProviderProfileService;
import com.vaishnavi.servicebook.service.ServiceService;
import com.vaishnavi.servicebook.service.WorkingHourService;
import com.vaishnavi.servicebook.userentity.ProviderProfile;
import com.vaishnavi.servicebook.userentity.ServiceEntity;
import com.vaishnavi.servicebook.userentity.WorkingHour;
import com.vaishnavi.servicebook.userentity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/provider")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderProfileService providerProfileService;
    private final ServiceService serviceService;
    private final WorkingHourService workingHourService;

    // =========================
    // GET PROVIDER PROFILE
    // =========================
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ProviderProfile>> getProfile(
            @AuthenticationPrincipal User user) {

        ProviderProfile profile = providerProfileService.getProfile(user);
        return ResponseEntity.ok(ApiResponse.success(profile, "Profile fetched successfully"));
    }

    // =========================
    // UPDATE PROVIDER PROFILE
    // =========================
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<ProviderProfile>> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody ProviderProfile profileData) {

        ProviderProfile updated = providerProfileService.update(user, profileData);

        return ResponseEntity.ok(ApiResponse.success(updated, "Profile updated successfully"));
    }

    // =========================
    // PROFILE COMPLETION STATUS
    // =========================
    @GetMapping("/profile/completion")
    public ResponseEntity<ApiResponse<Map<String, Object>>> profileCompletion(
            @AuthenticationPrincipal User user) {

        ProviderProfile profile = providerProfileService.getProfile(user);

        Map<String, Object> response = new HashMap<>();
        response.put("percentage", profile.getProfileCompletion());
        response.put("completed", profile.isProfileCompleted());

        response.put("businessNameCompleted", profile.getBusinessName() != null);
        response.put("phoneCompleted", profile.getPhone() != null);
        response.put("cityCompleted", profile.getCity() != null);
        response.put("addressCompleted", profile.getAddress() != null);
        response.put("experienceCompleted",
                profile.getExperience() != null && profile.getExperience() > 0);
        response.put("bioCompleted", profile.getBio() != null);

        return ResponseEntity.ok(ApiResponse.success(response, "Profile completion status fetched"));
    }

    // =========================
    // PROVIDER SERVICES
    // =========================
    @GetMapping("/services")
    public ResponseEntity<ApiResponse<List<ServiceEntity>>> getMyServices(
            @AuthenticationPrincipal User user) {
        ProviderProfile profile = providerProfileService.getProfile(user);
        List<ServiceEntity> services = serviceService.getServicesByProvider(profile);
        return ResponseEntity.ok(ApiResponse.success(services, "Services fetched successfully"));
    }

    @PostMapping("/services")
    public ResponseEntity<ApiResponse<ServiceEntity>> addService(
            @AuthenticationPrincipal User user,
            @RequestBody ServiceEntity service) {
        ProviderProfile profile = providerProfileService.getProfile(user);
        ServiceEntity created = serviceService.createServiceForProvider(profile.getId(), service);
        return ResponseEntity.ok(ApiResponse.success(created, "Service added successfully"));
    }

    @DeleteMapping("/services/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteService(
            @PathVariable Long id) {
        // TODO: check ownership strictly
        serviceService.deleteService(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Service deleted successfully"));
    }

    // =========================
    // WORKING HOURS
    // =========================
    @GetMapping("/working-hours")
    public ResponseEntity<ApiResponse<List<WorkingHour>>> getMyWorkingHours(
            @AuthenticationPrincipal User user) {
        ProviderProfile profile = providerProfileService.getProfile(user);
        List<WorkingHour> hours = workingHourService.getWorkingHoursByProvider(profile.getId());
        return ResponseEntity.ok(ApiResponse.success(hours, "Working hours fetched successfully"));
    }

    @PostMapping("/working-hours")
    public ResponseEntity<ApiResponse<WorkingHour>> addWorkingHour(
            @AuthenticationPrincipal User user,
            @RequestBody WorkingHour workingHour) {
        ProviderProfile profile = providerProfileService.getProfile(user);
        workingHour.setProviderProfile(profile);
        WorkingHour saved = workingHourService.saveWorkingHour(workingHour);
        return ResponseEntity.ok(ApiResponse.success(saved, "Working hour added successfully"));
    }

    @PostMapping("/working-hours/schedule")
    public ResponseEntity<ApiResponse<List<WorkingHour>>> updateSchedule(
            @AuthenticationPrincipal User user,
            @RequestBody WorkingScheduleDto dto) {
        ProviderProfile profile = providerProfileService.getProfile(user);
        List<WorkingHour> updated = workingHourService.updateSchedule(profile, dto);
        return ResponseEntity.ok(ApiResponse.success(updated, "Schedule updated successfully"));
    }
}

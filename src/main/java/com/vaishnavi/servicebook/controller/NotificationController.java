package com.vaishnavi.servicebook.controller;

import com.vaishnavi.servicebook.dto.ApiResponse;
import com.vaishnavi.servicebook.repository.ProviderProfileRepository;
import com.vaishnavi.servicebook.service.NotificationService;
import com.vaishnavi.servicebook.userentity.Notification;
import com.vaishnavi.servicebook.userentity.ProviderProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {
    private final NotificationService notificationService;
    private final ProviderProfileRepository providerRepo;

    public NotificationController(NotificationService notificationService, ProviderProfileRepository providerRepo) {
        this.notificationService = notificationService;
        this.providerRepo = providerRepo;
    }

    @GetMapping("/provider/{providerId}")
    public ResponseEntity<ApiResponse<List<Notification>>> getNotifications(@PathVariable Long providerId) {
        ProviderProfile provider = providerRepo.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        List<Notification> notifications = notificationService.getNotificationsForProvider(provider);
        return ResponseEntity.ok(ApiResponse.success(notifications, "Notifications fetched successfully"));
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Notification marked as read"));
    }
}

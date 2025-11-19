package com.vaishnavi.servicebook.controller;

import com.vaishnavi.servicebook.Service.BookingService;
import com.vaishnavi.servicebook.Userentity.ProviderProfile;
import com.vaishnavi.servicebook.Userentity.ServiceEntity;
import com.vaishnavi.servicebook.Userentity.User;
import com.vaishnavi.servicebook.Userentity.WorkingHour;
import com.vaishnavi.servicebook.repository.ProviderProfileRepository;
import com.vaishnavi.servicebook.repository.ServiceRepository; // Use this instead of ServiceOfferingRepository
import com.vaishnavi.servicebook.repository.UserRepository;
import com.vaishnavi.servicebook.repository.WorkingHourRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/providers")
public class
ProviderController {

    private final ProviderProfileRepository providerRepo;
    private final UserRepository userRepo;
    private final ServiceRepository serviceRepo; // ✅ Use ServiceRepository
    private final WorkingHourRepository workingRepo;
    private final BookingService bookingService;

    public ProviderController(ProviderProfileRepository providerRepo,
                              UserRepository userRepo,
                              ServiceRepository serviceRepo,
                              WorkingHourRepository workingRepo,
                              BookingService bookingService) {
        this.providerRepo = providerRepo;
        this.userRepo = userRepo;
        this.serviceRepo = serviceRepo;
        this.workingRepo = workingRepo;
        this.bookingService = bookingService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createProfile(@PathVariable Long userId, @RequestBody ProviderProfile profile) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        profile.setUser(user);
        providerRepo.save(profile);
        return ResponseEntity.ok(profile);
    }

    @PostMapping("/{providerId}/services")
    public ResponseEntity<?> addService(@PathVariable Long providerId, @RequestBody ServiceEntity service) {
        ProviderProfile provider = providerRepo.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        service.setProvider(provider);
        serviceRepo.save(service); // ✅ Save ServiceEntity
        return ResponseEntity.ok(service);
    }

    @PostMapping("/{providerId}/working-hours")
    public ResponseEntity<?> addWorkingHour(@PathVariable Long providerId, @RequestBody WorkingHour wh) {
        ProviderProfile provider = providerRepo.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
        wh.setProviderProfile(provider);
        workingRepo.save(wh);
        return ResponseEntity.ok(wh);
    }

    @GetMapping("/{providerId}/is-open")
    public ResponseEntity<?> isOpen(@PathVariable Long providerId, @RequestParam(required = false) String at) {
        LocalDateTime dateTime;
        if (at == null) dateTime = LocalDateTime.now();
        else dateTime = LocalDateTime.parse(at); // expects ISO like "2025-09-25T15:30:00"
        boolean open = bookingService.isProviderOpen(providerId, dateTime);
        return ResponseEntity.ok(open ? "OPEN" : "CLOSED");
    }
}

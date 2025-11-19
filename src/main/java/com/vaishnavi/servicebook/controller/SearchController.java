package com.vaishnavi.servicebook.controller;

import com.vaishnavi.servicebook.Userentity.ServiceEntity;
import com.vaishnavi.servicebook.Service.BookingService;
import com.vaishnavi.servicebook.repository.ServiceRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private final ServiceRepository serviceRepo;
    private final BookingService bookingService;

    public SearchController(ServiceRepository serviceRepo, BookingService bookingService) {
        this.serviceRepo = serviceRepo;
        this.bookingService = bookingService;
    }

    @GetMapping("/services")
    public ResponseEntity<?> searchServices(
            @RequestParam String keyword,
            @RequestParam(required = false) String dateTime
    ) {
        List<ServiceEntity> allServices = serviceRepo.findByServiceNameContainingIgnoreCase(keyword);

        if (dateTime != null) {
            LocalDateTime dt = LocalDateTime.parse(dateTime);
            allServices = allServices.stream()
                    .filter(s -> bookingService.isProviderOpen(s.getProvider().getUser().getId(), dt))
                    .collect(Collectors.toList());
        }

        List<Map<String, Object>> results = allServices.stream().map(s -> {
            Map<String, Object> data = new HashMap<>();
            data.put("serviceName", s.getServiceName());
            data.put("price", s.getPrice());
            data.put("durationMinutes", s.getDurationMinutes());
            data.put("providerName", s.getProvider().getBusinessName());
            return data;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }
}

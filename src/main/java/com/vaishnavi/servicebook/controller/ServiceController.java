package com.vaishnavi.servicebook.controller;

import com.vaishnavi.servicebook.dto.ApiResponse;
import com.vaishnavi.servicebook.service.ServiceService;
import com.vaishnavi.servicebook.userentity.ServiceEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ServiceEntity>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(serviceService.getAllServices(), "Services fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceEntity>> getById(@PathVariable Long id) {
        return ResponseEntity
                .ok(ApiResponse.success(serviceService.getServiceById(id), "Service fetched successfully"));
    }

    @PostMapping("/add/{providerId}")
    public ResponseEntity<ApiResponse<ServiceEntity>> createServiceForProvider(@PathVariable Long providerId,
            @RequestBody ServiceEntity service) {
        return ResponseEntity.ok(ApiResponse.success(serviceService.createServiceForProvider(providerId, service),
                "Service created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ServiceEntity>> update(@PathVariable Long id,
            @RequestBody ServiceEntity service) {
        return ResponseEntity
                .ok(ApiResponse.success(serviceService.updateService(id, service), "Service updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Service deleted successfully"));
    }
}

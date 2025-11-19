package com.vaishnavi.servicebook.controller;

import com.vaishnavi.servicebook.Service.ServiceService;
import com.vaishnavi.servicebook.Userentity.ServiceEntity;
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
    public List<ServiceEntity> getAll() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    public ServiceEntity getById(@PathVariable Long id) {
        return serviceService.getServiceById(id);
    }
    @PostMapping("/add/{providerId}")
    public ServiceEntity createServiceForProvider(@PathVariable Long providerId,
                                                  @RequestBody ServiceEntity service) {
        return serviceService.createServiceForProvider(providerId, service);
    }

    @PutMapping("/{id}")
    public ServiceEntity update(@PathVariable Long id, @RequestBody ServiceEntity service) {
        return serviceService.updateService(id, service);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.ok().build();
    }
}

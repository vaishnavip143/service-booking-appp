package com.vaishnavi.servicebook.service;

import com.vaishnavi.servicebook.userentity.ProviderProfile;
import com.vaishnavi.servicebook.userentity.ServiceEntity;
import com.vaishnavi.servicebook.repository.ProviderProfileRepository;
import com.vaishnavi.servicebook.repository.ServiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final ProviderProfileRepository providerRepository;

    // ✅ Constructor-based injection (best practice)
    public ServiceService(ServiceRepository serviceRepository, ProviderProfileRepository providerRepository) {
        this.serviceRepository = serviceRepository;
        this.providerRepository = providerRepository;
    }

    public ServiceEntity createServiceForProvider(Long providerId, ServiceEntity service) {
        ProviderProfile provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        service.setProvider(provider); // 🔗 linking service to provider
        return serviceRepository.save(service);
    }

    public List<ServiceEntity> getServicesByProvider(ProviderProfile provider) {
        return serviceRepository.findByProvider(provider);
    }

    public List<ServiceEntity> getAllServices() {
        return serviceRepository.findAll();
    }

    public ServiceEntity getServiceById(Long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }

    public ServiceEntity createService(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    public ServiceEntity updateService(Long id, ServiceEntity updated) {
        ServiceEntity service = getServiceById(id);
        service.setServiceName(updated.getServiceName());
        service.setPrice(updated.getPrice());
        service.setDurationMinutes(updated.getDurationMinutes());
        return serviceRepository.save(service);
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}

package com.vaishnavi.servicebook.Service;

import com.vaishnavi.servicebook.Userentity.ProviderProfile;
import com.vaishnavi.servicebook.Userentity.WorkingHour;
import com.vaishnavi.servicebook.repository.ProviderProfileRepository;
import com.vaishnavi.servicebook.repository.WorkingHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class WorkingHourService {

    @Autowired
    private WorkingHourRepository workingHourRepo;

    @Autowired
    private ProviderProfileRepository providerRepo;

    // ✅ Get all working hours
    public List<WorkingHour> getAllWorkingHours() {
        return workingHourRepo.findAll();
    }

    // ✅ Get working hours by provider ID
    public List<WorkingHour> getWorkingHoursByProvider(Long providerId) {
        ProviderProfile provider = providerRepo.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found with ID: " + providerId));
        return workingHourRepo.findByProviderProfile(provider);
    }

    // ✅ Save working hour (previous method)
    public WorkingHour saveWorkingHour(WorkingHour workingHour) {
        Long providerId = workingHour.getProviderProfile().getId();

        ProviderProfile provider = providerRepo.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found with ID: " + providerId));

        workingHour.setProviderProfile(provider);

        return workingHourRepo.save(workingHour);
    }
}

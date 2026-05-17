package com.vaishnavi.servicebook.service;

import com.vaishnavi.servicebook.userentity.ProviderProfile;
import com.vaishnavi.servicebook.userentity.WorkingHour;
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

    // ✅ Bulk update schedule
    public List<WorkingHour> updateSchedule(ProviderProfile provider,
            com.vaishnavi.servicebook.dto.WorkingScheduleDto dto) {
        List<WorkingHour> existing = workingHourRepo.findByProviderProfile(provider);

        // If no existing hours, initialize for Mon-Sun (1-7)
        if (existing.isEmpty()) {
            for (int i = 1; i <= 7; i++) {
                WorkingHour wh = new WorkingHour();
                wh.setDayOfWeek(i);
                wh.setProviderProfile(provider);
                wh.setClosed(true); // default closed
                existing.add(wh);
            }
        } else {
            // ensure all days 1-7 exist, add if missing
            for (int i = 1; i <= 7; i++) {
                int finalI = i;
                boolean exists = existing.stream().anyMatch(w -> w.getDayOfWeek() == finalI);
                if (!exists) {
                    WorkingHour wh = new WorkingHour();
                    wh.setDayOfWeek(i);
                    wh.setProviderProfile(provider);
                    wh.setClosed(true);
                    existing.add(wh);
                }
            }
        }

        // Apply DTO rules
        for (WorkingHour wh : existing) {
            if (dto.getOffDays() != null && dto.getOffDays().contains(wh.getDayOfWeek())) {
                wh.setClosed(true);
                wh.setOpenTime(null);
                wh.setCloseTime(null);
            } else {
                wh.setClosed(false);
                wh.setOpenTime(dto.getStartTime());
                wh.setCloseTime(dto.getEndTime());
            }
        }

        return workingHourRepo.saveAll(existing);
    }
}

package com.vaishnavi.servicebook.Service;
import com.vaishnavi.servicebook.Userentity.*;
import com.vaishnavi.servicebook.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final WorkingHourRepository workingHourRepo;
    private final ProviderProfileRepository providerRepo;

    public BookingService(WorkingHourRepository workingHourRepo,
                          ProviderProfileRepository providerRepo) {
        this.workingHourRepo = workingHourRepo;
        this.providerRepo = providerRepo;
    }

    /**
     * Check if provider is open at a given datetime
     */
    public boolean isProviderOpen(Long providerId, LocalDateTime dateTime) {
        ProviderProfile provider = providerRepo.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        // Get working hours for the day of week
        int dayOfWeek = dateTime.getDayOfWeek().getValue(); // 1 = Monday, 7 = Sunday
        List<WorkingHour> hours = workingHourRepo.findByProviderProfileAndDayOfWeek(provider, dayOfWeek);

        // Check if any working hour includes the given time
        for (WorkingHour wh : hours) {
            if (!wh.isClosed() &&
                    !dateTime.toLocalTime().isBefore(wh.getOpenTime()) &&
                    !dateTime.toLocalTime().isAfter(wh.getCloseTime())) {
                return true;
            }
        }
        return false;
    }
}

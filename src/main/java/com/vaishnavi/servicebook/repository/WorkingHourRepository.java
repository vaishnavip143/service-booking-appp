package com.vaishnavi.servicebook.repository;

import com.vaishnavi.servicebook.userentity.ProviderProfile;
import com.vaishnavi.servicebook.userentity.WorkingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingHourRepository extends JpaRepository<WorkingHour, Long> {
    boolean existsByProviderProfile(ProviderProfile providerProfile);
    // ✅ Find all working hours for a provider
    List<WorkingHour> findByProviderProfile(ProviderProfile provider);

    // ✅ Find working hours for a provider by day of week
    List<WorkingHour> findByProviderProfileAndDayOfWeek(ProviderProfile provider, int dayOfWeek);
}

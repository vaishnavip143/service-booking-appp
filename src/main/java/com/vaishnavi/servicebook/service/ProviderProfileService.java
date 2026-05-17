package com.vaishnavi.servicebook.service;

import com.vaishnavi.servicebook.repository.ProviderProfileRepository;
import com.vaishnavi.servicebook.repository.ServiceRepository;
import com.vaishnavi.servicebook.repository.WorkingHourRepository;
import com.vaishnavi.servicebook.userentity.ProviderProfile;
import com.vaishnavi.servicebook.userentity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class ProviderProfileService {

    private final ProviderProfileRepository repo;

    public ProviderProfile getProfile(User user) {
        return repo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    public ProviderProfile update(User user, ProviderProfile data) {
        ProviderProfile p = getProfile(user);

        p.setBusinessName(data.getBusinessName());
        p.setPhone(data.getPhone());
        p.setCity(data.getCity());
        p.setAddress(data.getAddress());
        p.setExperience(data.getExperience());
        p.setBio(data.getBio());

        calculateCompletion(p);
        return repo.save(p);
    }

    private void calculateCompletion(ProviderProfile p) {
        int score = 0;
        if (p.getBusinessName() != null) score += 20;
        if (p.getPhone() != null) score += 20;
        if (p.getCity() != null) score += 20;
        if (p.getAddress() != null) score += 20;
        if (p.getBio() != null) score += 20;

        p.setProfileCompletion(score);
        p.setProfileCompleted(score == 100);
    }
}

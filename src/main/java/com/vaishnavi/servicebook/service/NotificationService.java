package com.vaishnavi.servicebook.service;

import com.vaishnavi.servicebook.repository.NotificationRepository;
import com.vaishnavi.servicebook.userentity.Notification;
import com.vaishnavi.servicebook.userentity.ProviderProfile;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void createNotification(ProviderProfile provider, String message, Long appointmentId) {
        Notification notification = Notification.builder()
                .provider(provider)
                .message(message)
                .appointmentId(appointmentId)
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsForProvider(ProviderProfile provider) {
        return notificationRepository.findByProviderOrderByCreatedAtDesc(provider);
    }

    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(n -> {
            n.setRead(true);
            notificationRepository.save(n);
        });
    }
}

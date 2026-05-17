package com.vaishnavi.servicebook.userentity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ProviderProfile provider;

    private String message;
    private Long appointmentId;
    private LocalDateTime createdAt;
    private boolean isRead;
}

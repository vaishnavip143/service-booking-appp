package com.vaishnavi.servicebook.Userentity;

import lombok.*; // make sure this import exists
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User customer;

    @ManyToOne
    private ProviderProfile provider;

    @ManyToOne
    private ServiceEntity service;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status; // ✅ Must be AppointmentStatus, NOT UserType
}

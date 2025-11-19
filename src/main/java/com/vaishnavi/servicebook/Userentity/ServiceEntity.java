package com.vaishnavi.servicebook.Userentity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "service_entity") // ✅ make it match your FK table name
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;
    private double price;
    private int durationMinutes;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ProviderProfile provider;
}

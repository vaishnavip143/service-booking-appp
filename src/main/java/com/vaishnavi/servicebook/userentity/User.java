package com.vaishnavi.servicebook.userentity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "providerProfile")
@Entity
@Table(name = "app_users") // Renamed to avoid reserved keyword conflicts
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    // ✅ ONE-WAY RELATION (CORRECT)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ProviderProfile providerProfile;
}

// src/main/java/com/vaishnavi/servicebook/Userentity/User.java
package com.vaishnavi.servicebook.Userentity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_user") // avoid SQL reserved word 'user'
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String name;

    @Enumerated(EnumType.STRING)
    private UserType userType;
    // User.java
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "provider_profile_id")
    @JsonManagedReference
    private ProviderProfile providerProfile;
}

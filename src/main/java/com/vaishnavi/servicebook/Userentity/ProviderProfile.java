   package com.vaishnavi.servicebook.Userentity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vaishnavi.servicebook.Userentity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessName;
    private String address;
    // ProviderProfile.java
    @OneToOne(mappedBy = "providerProfile")
    @JsonBackReference
    private User user;
}

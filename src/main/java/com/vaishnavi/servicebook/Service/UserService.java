package com.vaishnavi.servicebook.Service;

import com.vaishnavi.servicebook.Userentity.ProviderProfile;
import com.vaishnavi.servicebook.Userentity.User;
import com.vaishnavi.servicebook.Userentity.UserType;
import com.vaishnavi.servicebook.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User createProviderUser(String username, String password, String businessName, String address) {
        ProviderProfile provider = ProviderProfile.builder()
                .businessName(businessName)
                .address(address)
                .build();

        User user = User.builder()
                .username(username)
                .password(password)
                .userType(UserType.PROVIDER)
                .providerProfile(provider)
                .build();

        // Link back
        provider.setUser(user);

        return userRepository.save(user);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updated) {
        User user = getUserById(id);
        user.setUsername(updated.getUsername());
        user.setPassword(updated.getPassword());
        user.setUserType(updated.getUserType());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}


package be.twice.service;

import be.twice.model.Role;
import be.twice.model.User;
import be.twice.model.UserDTO;
import be.twice.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> registerUser(@Valid UserDTO userDTO) {
        try {
            String hashedPassword = passwordEncoder.encode(userDTO.getPassword());

            User user = new User();
            user.setEmail(userDTO.getEmail());
            user.setUsername(userDTO.getUsername());
            user.setRole(Role.USER);
            user.setPassword(hashedPassword);

            repository.insert(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    public User updateUser(String userId, UserDTO userDTO) {
        User user = repository.findUserById(userId);
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        repository.save(user);
        return user;
    }

    public User getUserById(String userId) {
        return repository.findUserById(userId);
    }

    public void deleteUser(String userId) {
        repository.deleteById(userId);
    }
}

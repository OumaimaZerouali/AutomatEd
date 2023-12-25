package be.twice.service;

import be.twice.model.Role;
import be.twice.model.User;
import be.twice.model.UserDTO;
import be.twice.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setRole(Role.USER);

        repository.insert(user);
        return user;
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

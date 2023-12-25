package be.twice.service;

import be.twice.model.Role;
import be.twice.model.User;
import be.twice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public String home() {
        return "Home";
    }

    public void insertNewUser() {
        User user = new User();
        user.setEmail("gabriel.delapena@email.com");
        user.setPassword("helloworld");
        user.setUsername("gabriel");
        user.setRole(Role.STUDENT);

        repository.insert(user);
    }
}

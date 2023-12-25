package be.twice.controller;

import be.twice.model.User;
import be.twice.model.UserDTO;
import be.twice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/insert-user")
    public User registerUser(@RequestBody UserDTO userDTO) {
        return service.registerUser(userDTO);
    }

    @PutMapping("/update-user")
    public User updateUser(@RequestParam("userId") String userId,
                           @RequestBody UserDTO userDTO) {
        return service.updateUser(userId, userDTO);
    }

    @GetMapping("/get-user")
    public User getUserById(@RequestParam("userId") String userId) {
        return service.getUserById(userId);
    }

    @DeleteMapping("/delete-user")
    public void deleteUser(@RequestParam("userId") String userId) {
        service.deleteUser(userId);
    }
}

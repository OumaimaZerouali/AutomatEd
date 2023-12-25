package be.twice.controller;

import be.twice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/home")
    public String home() {
        return service.home();
    }

    @PostMapping("/insert-user")
    public void insertUser() {
        service.insertNewUser();
    }
}

package be.twice.controller;

import be.twice.model.User;
import be.twice.model.UserDTO;
import be.twice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserDTO userDTO) {
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return errors;
    }
}

package be.twice.service;

import be.twice.model.Role;
import be.twice.model.User;
import be.twice.model.UserDTO;
import be.twice.model.request.UserLoginRequest;
import be.twice.model.request.UserRegisterRequest;
import be.twice.repository.UserRepository;
import be.twice.token.GenerateToken;
import be.twice.token.TokenParser;
import io.jsonwebtoken.Claims;
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

    public ResponseEntity<?> registerUser(@Valid UserRegisterRequest userRegisterRequest) {
        try {
            String hashedPassword = passwordEncoder.encode(userRegisterRequest.getPassword());

            User user = new User();
            user.setEmail(userRegisterRequest.getEmail());
            user.setUsername(userRegisterRequest.getUsername());
            user.setRole(Role.USER);
            user.setPassword(hashedPassword);

            repository.insert(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public ResponseEntity<?> loginUser(UserLoginRequest userLoginRequest) {
        try {
            User user = repository.findUserByUsername(userLoginRequest.getUsername());

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }

            if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }

            GenerateToken generateToken = new GenerateToken();
            String token = generateToken.generate(user.getId(), user.getRole().toString());

            return ResponseEntity.ok(token);
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

    public ResponseEntity<?> getCurrentUserById(String token) {
        try {
            TokenParser tokenParser = new TokenParser();
            Claims claims = tokenParser.getTokenPayload(token);

            User currentUser = repository.findUserById(claims.getSubject());
            return ResponseEntity.status(HttpStatus.OK).body(currentUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    public void deleteUser(String userId) {
        repository.deleteById(userId);
    }
}

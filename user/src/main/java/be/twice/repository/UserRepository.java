package be.twice.repository;

import be.twice.model.User;
import be.twice.model.UserDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
    User insert(UserDTO userDTO);
    User findUserById(String userId);
    void deleteById(String userId);
    User findUserByUsername(String username);
}

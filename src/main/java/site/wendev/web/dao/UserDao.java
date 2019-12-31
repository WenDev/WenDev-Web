package site.wendev.web.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import site.wendev.web.domain.User;

public interface UserDao extends MongoRepository<User, String> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByUsernameAndPassword(String username, String password);
}

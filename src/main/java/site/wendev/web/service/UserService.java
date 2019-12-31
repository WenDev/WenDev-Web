package site.wendev.web.service;

import site.wendev.web.domain.User;

import java.util.List;

/**
 * 用户相关业务逻辑
 *
 * @author 江文
 */
public interface UserService {
    User login(String username, String password);
    User emailLogin(String email, String password);
    User register(User user);
    User modify(User user);
    User delete(String userId);
    List<User> findAll();
}

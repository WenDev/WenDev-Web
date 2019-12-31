package site.wendev.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.wendev.web.dao.UserDao;
import site.wendev.web.domain.User;
import site.wendev.web.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return User类的一个对象，登录失败时为null，登录成功时为登录成功的用户
     */
    @Override
    public User login(String username, String password) {
        User user = userDao.findByUsername(username);

        // 用户不存在
        if (user == null) {
            System.out.println(username + "用户不存在");
            return null;

        // 密码错误
        } else if (!user.getPassword().equals(password)) {
            System.out.println(username + "的密码错误");
            return null;

        // 登录成功
        } else {
            System.out.println(username + "登录成功");
            return user;
        }
    }

    /**
     * 使用邮箱的登录
     *
     * @param email 用户邮箱
     * @param password 密码
     * @return User类的一个对象，登录失败时为null，登录成功时为登录成功的用户
     */
    @Override
    public User emailLogin(String email, String password) {
        User user = userDao.findByEmail(email);

        // 用户不存在
        if (user == null) {
            System.out.println("使用" + email + "邮箱的用户不存在");
            return null;

            // 密码错误
        } else if (!user.getPassword().equals(password)) {
            System.out.println("使用" + email + "邮箱的用户的密码错误");
            return null;

            // 登录成功
        } else {
            System.out.println("使用" + email + "邮箱的用户登录成功");
            return user;
        }
    }

    /**
     * 用户注册
     *
     * @param user 要注册的用户
     * @return 注册失败（用户名或邮箱已被使用）时为null，成功时为注册的用户
     */
    @Override
    public User register(User user) {
        User user1 = userDao.findByUsername(user.getUsername());
        User user2 = userDao.findByEmail(user.getEmail());

        // 用户名已被使用，注册失败
        if (user1 != null) {
            System.out.println(user.getUsername() + "用户名已被使用");
            return null;

        // 邮箱已被使用，注册失败
        } else if (user2 != null) {
            System.out.println(user.getEmail() + "邮箱已被使用");
            return null;

        // 用户名未被使用，注册成功
        } else {
            user.setRole("superAdmin");   // 设置用户的权限为user
            userDao.insert(user);   // 插入用户记录
            return user;
        }
    }

    /**
     * 编辑用户信息
     *
     * @param user 要编辑的用户
     * @return 编辑并保存成功时为被编辑的用户，失败（没有找到或用户名/邮箱已被使用）时为null
     */
    @Override
    public User modify(User user) {
        Optional<User> userOptional = userDao.findById(user.getId());

        // 如果Optional的Value不为空（找到用户），就将编辑后的用户存入数据库
        if (userOptional.isPresent()) {
            User user1 = userOptional.get();

            // 调用DAO层的insert方法直接将更新后的用户数据插入到数据库中
            System.out.println(user1.getUsername() + "修改信息成功");
            return userDao.save(user);
        } else {
            System.out.println("没有找到ID为" + user.getId() + "的用户");
            return null;
        }
    }

    /**
     * 删除用户
     *
     * @param userId 要删除的用户的ID
     * @return 被删除的用户，没有找到则返回null
     */
    @Override
    public User delete(String userId) {
        Optional<User> userOptional = userDao.findById(userId);

        // 如果Optional的Value不为空（找到），就得到Value后删除这个用户
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // 调用DAO层的insert方法直接将更新后的用户数据插入到数据库中
            userDao.delete(user);
            return user;
        } else {
            System.out.println("没有找到ID为" + userId + "的用户");
            return null;
        }
    }

    /**
     * 查找已注册的全部用户
     *
     * @return 一个List，内容为所有已注册的用户
     */
    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}

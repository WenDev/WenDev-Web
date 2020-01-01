package site.wendev.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import site.wendev.web.domain.User;
import site.wendev.web.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping(value = "/register")
    public String registerPage() {
        return "register";
    }

    /**
     * 使用了Spring Security之后，这个自定义的登录逻辑就不需要了
     * @deprecated
     */
    public String login(String username, String password, RedirectAttributes attributes) {
        if (username == null || password == null) {
            attributes.addFlashAttribute("errInfo", "用户名或密码不能为空！");
            return "redirect:/login";
        }

        User user = userService.login(username, password);

        if (user != null) {
            session.setAttribute("userId", user.getId());
            System.out.printf("%s : %s 已经成功登录", user.getUsername(), user.getPassword());
            return "redirect:/index";
        } else {
            attributes.addFlashAttribute("errInfo", "登录失败：用户名或密码错误");
            return "redirect:/login";
        }
    }

    @PostMapping("/register")
    public String register(String username, String password, String email, String nickname) {
        User regUser = userService.register(new User()
                .setUsername(username).setPassword(password).setEmail(email)
                .setNickname(nickname).setRegisterDate(new Date()));
        if (regUser != null) {
            return "redirect:/login";
        } else {
            return "redirect:/register?error";
        }
    }
}

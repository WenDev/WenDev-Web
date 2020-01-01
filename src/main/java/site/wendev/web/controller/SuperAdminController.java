package site.wendev.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.wendev.web.service.UserService;

@Controller
@RequestMapping(value = "/superAdmin")
public class SuperAdminController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/findAll")
    public String findAllUser(Model model) {
        model.addAttribute("users", userService.findAll());
        return "superAdmin/findAll";
    }
}

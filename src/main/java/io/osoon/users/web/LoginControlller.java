package io.osoon.users.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControlller {

    @GetMapping("/loginfail")
    public String loginFail(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

}

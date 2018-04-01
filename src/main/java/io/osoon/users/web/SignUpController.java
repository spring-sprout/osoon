package io.osoon.users.web;

import io.osoon.users.User;
import io.osoon.users.UserRepository;
import io.osoon.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class SignUpController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute("user", new UserSignUpDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signUpSubmit(@ModelAttribute("user") @Valid UserSignUpDto userSignUpDto, BindingResult result) {
        if (result.hasErrors()) {
            return "signup";
        }

        Optional<User> optionalUser = userRepository.findByEmail(userSignUpDto.getEmail());
        if (optionalUser.isPresent()) {
            result.rejectValue("email", null, "The email is already registered. Please use another email address or try to find your password for it.");
            return "signup";
        }

        userService.registerNewUser(userSignUpDto);
        return "redirect:/signup?success";
    }

}

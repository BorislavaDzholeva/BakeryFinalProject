package BakeryProject.demo.web;

import BakeryProject.demo.models.DTO.UserRegistrationDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    @ModelAttribute
    public UserRegistrationDTO userRegistrationDTO(){
        return new UserRegistrationDTO();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }
    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() || !userRegistrationDTO.getPassword()
                .equals(userRegistrationDTO.getConfirmPassword())) {

            redirectAttributes.addFlashAttribute
                    ("userRegistrationDTO", userRegistrationDTO);
            redirectAttributes.addFlashAttribute
                    ("org.springframework.validation.BindingResult.userRegistrationDTO", bindingResult);

            return "redirect:register";

        }
        return "redirect:login";

    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}

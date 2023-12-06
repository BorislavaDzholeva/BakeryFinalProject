package BakeryProject.demo.web;

import  BakeryProject.demo.models.DTO.UserRegistrationDTO;
import BakeryProject.demo.models.view.OrderDetailsView;
import BakeryProject.demo.models.view.UserView;
import BakeryProject.demo.service.OrderService;
import BakeryProject.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;

    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @ModelAttribute
    public UserRegistrationDTO userRegistrationDTO() {
        return new UserRegistrationDTO();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegistrationDTO", userRegistrationDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegistrationDTO", bindingResult);

            return "redirect:/users/register";
        }
        userService.registerUser(userRegistrationDTO);
        return "redirect:/users/login";
    }

    @GetMapping("/profile/")
    private String profile(Principal principal, Model model){
        UserView userView = userService.getUserView(principal.getName());
        model.addAttribute("userView", userView);
        return "profile";
    }

    @GetMapping("orderDetails/{id}")
    public String orderDetails(@PathVariable("id") Long id, Model model){
        OrderDetailsView orderDetailsView = orderService.getOrderDetailsView(id);
        model.addAttribute("orderDetailsView", orderDetailsView);
        return "order-details";

    }



}

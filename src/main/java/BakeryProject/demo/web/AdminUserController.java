package BakeryProject.demo.web;

import BakeryProject.demo.models.DTO.AddUserDTO;
import BakeryProject.demo.models.entity.User;
import BakeryProject.demo.service.ProductService;
import BakeryProject.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String all(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("users", allUsers);
        return "admin/admin";
    }

    @GetMapping("/user/add")
    public String userAdd(Model model) {
        return "admin/add_user";
    }

    @ModelAttribute
    public AddUserDTO addUserDTO() {
        return new AddUserDTO();
    }

    @PostMapping("/user/add")
    public String userAddConfirm(@Valid AddUserDTO addUserDTO, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute
                    ("addUserDTO", addUserDTO);
            redirectAttributes.addFlashAttribute
                    ("org.springframework.validation.BindingResult.addUserDTO", bindingResult);

            return "redirect:/admin/user/add";
        }
        userService.addUser(addUserDTO);
        return "redirect:/admin/";
    }

    @GetMapping("/user/edit/{id}")
    public String userEdit(@PathVariable Long id, Model model) {
        AddUserDTO userData = userService.findUserById(id);
        model.addAttribute("userData", userData);
        return "admin/edit_user";
    }

    @PostMapping("/user/edit/")
    public String userEditConfirm(@Valid AddUserDTO addUserDTO, BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute
                    ("addUserDTO", addUserDTO);
            redirectAttributes.addFlashAttribute
                    ("org.springframework.validation.BindingResult.addUserDTO", bindingResult);
            return "redirect:/admin/user/edit/" + addUserDTO.getId();
        }
        userService.updateUser(addUserDTO);
        return "redirect:/admin/";
    }

    @GetMapping("/removeUser/{id}")
    public String removeUser(@PathVariable Long id) {
        userService.removeUserById(id);
        return "redirect:/admin/";
    }
}

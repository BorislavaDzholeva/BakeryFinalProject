package BakeryProject.demo.web;
import BakeryProject.demo.models.DTO.AdminAddIpDTO;
import BakeryProject.demo.models.entity.IPBlackList;
import BakeryProject.demo.service.IPBlackListService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/ip_black_list")
public class AdminIPBlacklist {
    private final IPBlackListService ipBlackListService;

    public AdminIPBlacklist(IPBlackListService ipBlackListService) {
        this.ipBlackListService = ipBlackListService;
    }

    @GetMapping("/")
    public String all(Model model) {
        List<IPBlackList> ipBlackList = ipBlackListService.getAllBlackListIp();
        model.addAttribute("ipBlackList", ipBlackList);
        return "admin/ip_black_list";
    }

    @ModelAttribute
    public AdminAddIpDTO adminAddIpDTO() {
        return new AdminAddIpDTO();
    }


    @GetMapping("/add/")
    public String ipAdd() {
        return "admin/add_ip_to_black_list";
    }

    @PostMapping("/add/")
    public String addIPConfirm(@Valid AdminAddIpDTO adminAddIpDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("adminAddIpDTO", adminAddIpDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.adminAddIpDTO", bindingResult);

            return "redirect:/admin/ip_black_list/add/";
        }
        ipBlackListService.addIP(adminAddIpDTO);
        return "redirect:/admin/ip_black_list/";
    }

    @GetMapping("/removeIP/{id}")
    public String removeReview(@PathVariable Long id) {
        ipBlackListService.removeIPById(id);
        return "redirect:/admin/ip_black_list/";
    }


}

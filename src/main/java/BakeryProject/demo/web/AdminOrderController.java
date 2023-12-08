package BakeryProject.demo.web;

import BakeryProject.demo.models.entity.Order;
import BakeryProject.demo.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {
    private final OrderService orderService;

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/")
    public String allOrders(Model model) {
        List<Order> allOrders = orderService.getAllOrders();
        model.addAttribute("allOrders", allOrders);
        return "admin/orders";
    }
    @GetMapping("/status/{id}")
    public String statusButton(@PathVariable Long id) {
        Order order = orderService.findById(id);
        orderService.changeStatus(order);
        return "redirect:/admin/orders/";
    }
}

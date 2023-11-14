package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.entity.Order;
import BakeryProject.demo.models.entity.User;
import BakeryProject.demo.models.enums.OrderStatusEnum;
import BakeryProject.demo.repository.OrderRepository;
import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public void changeStatus(Order order) throws IllegalAccessException {
        switch (order.getOrderStatus().name()) {
            case "Pending":
                order.setOrderStatus(OrderStatusEnum.Shipped);
                orderRepository.save(order);
                break;
            case "Shipped":
                User user = userRepository.findById(order.getUser().getId()).orElse(null);
                if (user == null) {
                    throw new IllegalAccessException("User is not found!");
                }
                break;
        }
    }
}

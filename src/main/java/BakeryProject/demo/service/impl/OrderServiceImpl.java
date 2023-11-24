package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.DTO.CreateOrderDTO;
import BakeryProject.demo.models.entity.CartItem;
import BakeryProject.demo.models.entity.Order;
import BakeryProject.demo.models.entity.OrderItem;
import BakeryProject.demo.models.entity.UserEntity;
import BakeryProject.demo.models.enums.OrderStatusEnum;
import BakeryProject.demo.repository.CartItemRepository;
import BakeryProject.demo.repository.OrderRepository;
import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;


    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartItemRepository cartItemRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;

        this.cartItemRepository = cartItemRepository;
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
                UserEntity user = userRepository.findById(order.getUser().getId()).orElse(null);
                if (user == null) {
                    throw new IllegalAccessException("User is not found!");
                }
                break;
        }
    }

    @Override
    public void createOrder(CreateOrderDTO createOrderDTO, String currentUserUsername) {
        UserEntity user = userRepository.findByUsername(currentUserUsername).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User is not found!");

        }

        Order order = new Order();
        List<CartItem> userCartItems = user.getCart().getCartItems();
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : userCartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItems.add(orderItem);
        }

        BigDecimal totalPrice = orderItems.stream().map(orderItem -> orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setOrderItems(orderItems);
        order.setCity(createOrderDTO.getCity());
        order.setAddress(createOrderDTO.getAddress());
        order.setPhoneNumber(createOrderDTO.getPhoneNumber());
        order.setTotalPrice(totalPrice);
        order.setUser(user);
        order.setOrderStatus(OrderStatusEnum.Pending);
        orderRepository.save(order);

        user.getCart().setCartItems(new ArrayList<>());
        userRepository.save(user);
        for (CartItem cartItem : userCartItems) {
            cartItemRepository.deleteById(cartItem.getId());
        }
    }

}


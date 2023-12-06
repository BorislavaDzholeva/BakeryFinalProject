package BakeryProject.demo.service.impl;

import BakeryProject.demo.event.OrderShippedEvent;
import BakeryProject.demo.models.DTO.CreateOrderDTO;
import BakeryProject.demo.models.entity.CartItem;
import BakeryProject.demo.models.entity.Order;
import BakeryProject.demo.models.entity.OrderItem;
import BakeryProject.demo.models.entity.UserEntity;
import BakeryProject.demo.models.enums.OrderStatusEnum;
import BakeryProject.demo.models.view.OrderDetailsView;
import BakeryProject.demo.repository.CartItemRepository;
import BakeryProject.demo.repository.OrderRepository;
import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.OrderService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ApplicationEventPublisher applicationEventPublisher;


    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository, CartItemRepository cartItemRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Override
    public void changeStatus(Order order) throws IllegalAccessException {
        switch (order.getOrderStatus().name()) {
            case "Pending":
                order.setOrderStatus(OrderStatusEnum.Shipped);
                orderRepository.save(order);
                OrderShippedEvent orderShippedEvent = new OrderShippedEvent(this, order.getId(), order.getUser().getEmail());
                applicationEventPublisher.publishEvent(orderShippedEvent);
                break;
            case "Shipped":
                UserEntity user = userRepository.findById(order.getUser().getId()).orElseThrow();
                break;
        }
    }

    @Override
    public void createOrder(CreateOrderDTO createOrderDTO, String currentUserUsername) {
        UserEntity user = userRepository.findByUsername(currentUserUsername).orElseThrow();
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

    @Override
    public OrderDetailsView getOrderDetailsView(Long id) {
        Order order = orderRepository.findById(id).orElseThrow();
        OrderDetailsView orderDetailsView = new OrderDetailsView();
        orderDetailsView.setId(order.getId());
        orderDetailsView.setProduct(order.getOrderItems().get(0).getProduct());
        orderDetailsView.setPrice(order.getOrderItems().get(0).getPrice());
        orderDetailsView.setQuantity(order.getOrderItems().get(0).getQuantity());
        orderDetailsView.setUser(order.getUser());
        return orderDetailsView;

    }
}


package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.CreateOrderDTO;
import BakeryProject.demo.models.entity.Order;
import BakeryProject.demo.models.view.OrderDetailsView;


import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();

    Order findById(Long id);

    void changeStatus(Order order) throws IllegalAccessException;

    void createOrder(CreateOrderDTO createOrderDTO, String currentUserUsername);


    OrderDetailsView getOrderDetailsView(Long id);
}

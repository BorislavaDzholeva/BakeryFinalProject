package BakeryProject.demo.models.view;

import BakeryProject.demo.models.entity.OrderItem;
import java.util.List;

public class OrderDetailsView {
    private Long id;
    List<OrderItem> orderItems;
    public OrderDetailsView() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }


}

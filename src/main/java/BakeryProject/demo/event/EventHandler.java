package BakeryProject.demo.event;

import BakeryProject.demo.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventHandler {
    private final EmailService emailService;

    public EventHandler(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener(OrderShippedEvent.class)
    public void onOrderShipped(OrderShippedEvent event) {
        emailService.sendEmail
                (event.getUserEmail(), "Your order has been shipped", "Dear customer, your order #" + event.getOrderId() + " has been SHIPPED and should arrive at your address in 30 minutes. Thank you for your purchase! We would love to hear your feedback!");
    }
}

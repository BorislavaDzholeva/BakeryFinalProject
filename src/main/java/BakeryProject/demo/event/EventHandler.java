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
                (event.getUserEmail(), "Your order has been shipped", "Your order #" + event.getOrderId() + " has been shipped.");
    }
}

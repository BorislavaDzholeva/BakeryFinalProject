package BakeryProject.demo.event;

import org.springframework.context.ApplicationEvent;

public class WeekendAvailableProductsEvent extends ApplicationEvent {
    private final String userEmail;


    public WeekendAvailableProductsEvent(Object source, String userEmail) {
        super(source);
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }
}

package BakeryProject.demo.scheduler;

import BakeryProject.demo.event.WeekendAvailableProductsEvent;
import BakeryProject.demo.models.entity.UserEntity;
import BakeryProject.demo.service.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MarketingEmailTask {
    private final UserService userService;
    private final ApplicationEventPublisher applicationEventPublisher;


    public MarketingEmailTask(UserService userService, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    //    @Scheduled(cron = "0 0 11 * * FRI",zone = "Europe/Sofia")
//    @Async
    public void execute() {

        // Send weekly email to all users: disabled because Outlook have limits
//        userService.getAllUsers().forEach(user -> {
//            WeekendAvailableProductsEvent weekendAvailableProductsEvent = new WeekendAvailableProductsEvent(this, user.getEmail());
//            applicationEventPublisher.publishEvent(weekendAvailableProductsEvent);
//        });

        UserEntity user = userService.findUserByUsername("admin");
        WeekendAvailableProductsEvent weekendAvailableProductsEvent = new WeekendAvailableProductsEvent(this, user.getEmail());
        applicationEventPublisher.publishEvent(weekendAvailableProductsEvent);
    }
}

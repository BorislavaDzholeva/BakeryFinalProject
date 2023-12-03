package BakeryProject.demo.scheduler;

import BakeryProject.demo.service.EmailService;
import BakeryProject.demo.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MarketingEmailTask {

    private final EmailService emailService;
    private final UserService userService;

    public MarketingEmailTask(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 11 * * FRI",zone = "Europe/Sofia")
    public void execute() {
        userService.getAllUsers().forEach(user -> {
            emailService.sendEmail(user.getEmail(), "Weekly reminder!", "Check out our products available only in the Weekend!");
        });
    }
}

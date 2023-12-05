package BakeryProject.demo.scheduler;
import BakeryProject.demo.service.IPBlackListService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RemoveBlacklistedIPsTask {

    private final IPBlackListService ipBlackListService;

    public RemoveBlacklistedIPsTask(IPBlackListService ipBlackListService) {
        this.ipBlackListService = ipBlackListService;
    }

    @Scheduled(fixedRate = 1800000)
    public void execute() {
        System.out.println("Removing expired IPs from blacklist...");
        ipBlackListService.removeExpired();
    }


}

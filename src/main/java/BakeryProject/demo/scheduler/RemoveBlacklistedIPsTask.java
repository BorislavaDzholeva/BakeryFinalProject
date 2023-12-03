package BakeryProject.demo.scheduler;

import BakeryProject.demo.interceptors.BlacklistIPInterceptor;
import BakeryProject.demo.service.IPBlackListService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RemoveBlacklistedIPsTask {

    private final IPBlackListService ipBlackListService;

    public RemoveBlacklistedIPsTask(IPBlackListService ipBlackListService) {
        this.ipBlackListService = ipBlackListService;
    }

    @Scheduled(fixedRate = 60000)
    public void execute() {
        ipBlackListService.removeExpired();
    }


}

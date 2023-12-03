package BakeryProject.demo.interceptors;

import BakeryProject.demo.exception.BlockedIPException;
import BakeryProject.demo.service.IPBlackListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
@Component
public class BlacklistIPInterceptor implements HandlerInterceptor {
    private final IPBlackListService ipBlackListService;
    public BlacklistIPInterceptor(IPBlackListService ipBlackListService) {
        this.ipBlackListService = ipBlackListService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();
        //Local address 127.0.0.1
        if (ipBlackListService.isBlocked(ipAddress)) {
            throw new BlockedIPException("You are blocked");
        }
        return true;
    }

}

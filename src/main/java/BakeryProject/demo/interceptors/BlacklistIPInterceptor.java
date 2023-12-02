package BakeryProject.demo.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

public class BlacklistIPInterceptor implements HandlerInterceptor {
    private List<String> blacklistedIpAddresses = new ArrayList<>();

    public BlacklistIPInterceptor() {
        blacklistedIpAddresses.add("192.168.100.1");
//        blacklistedIpAddresses.add("127.0.0.1");

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddress = request.getRemoteAddr();
        System.out.println(ipAddress);
        if (blacklistedIpAddresses.contains(ipAddress)) {
            throw new AccessDeniedException("You are not allowed to access this page");
        }
        return true;
    }

}

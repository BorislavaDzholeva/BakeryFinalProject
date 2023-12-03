package BakeryProject.demo.config;

import BakeryProject.demo.interceptors.BlacklistIPInterceptor;
import BakeryProject.demo.service.IPBlackListService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfigurator implements WebMvcConfigurer {
    private final IPBlackListService ipBlackListService;

    public InterceptorConfigurator(IPBlackListService ipBlackListService) {
        this.ipBlackListService = ipBlackListService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BlacklistIPInterceptor(ipBlackListService));
    }
}

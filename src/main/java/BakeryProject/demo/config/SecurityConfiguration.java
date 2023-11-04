package BakeryProject.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//       return httpSecurity.authorizeHttpRequests(authorizeRequest ->
//                authorizeRequest.anyRequest().permitAll()
//
//
//        ).formLogin(
//                formLogin -> {
//                    formLogin.loginPage("/users/login")
//                            .usernameParameter("username")
//                            .passwordParameter("password")
//                            .defaultSuccessUrl("/")
//                            .failureForwardUrl("/users/login-error");
//                }
//        ).logout(
//                logout -> {
//                    logout.logoutUrl("/users/logout")
//                            .logoutSuccessUrl("/")
//                            .invalidateHttpSession(true);
//                }
//        ).build();
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest()
                    .permitAll())
            .csrf(AbstractHttpConfigurer::disable);
    return http.build();
}

}

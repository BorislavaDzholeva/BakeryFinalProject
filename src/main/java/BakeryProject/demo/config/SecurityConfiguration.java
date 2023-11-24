package BakeryProject.demo.config;

import BakeryProject.demo.models.enums.RoleEnum;
import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.impl.AppUserDetailService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return httpSecurity.authorizeHttpRequests(authorizeRequest ->
                authorizeRequest.
                        requestMatchers("/admin/assets/**","/css/**","/images/**","/img/**","/js/**","/lib/**").permitAll().
                        requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/users/login", "/users/register").permitAll()
                        .requestMatchers("/about").permitAll()
                        .requestMatchers("/contacts").permitAll()
                        .requestMatchers("/products/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/admin/**").hasRole(RoleEnum.Administrator.name())
                        .requestMatchers(HttpMethod.POST,"/admin/**").hasRole(RoleEnum.Administrator.name())
                        .anyRequest().authenticated()


        ).csrf(csrf -> csrf.disable())
        .formLogin(
                formLogin -> {
                    formLogin.loginPage("/users/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/")
                            .failureUrl("/users/login?error=true");
                }
        ).logout(
                logout -> {
                    logout.logoutUrl("/users/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                }
        ).build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new AppUserDetailService(userRepository);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

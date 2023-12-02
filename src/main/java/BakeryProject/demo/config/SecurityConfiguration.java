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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//                .csrf((csrf) -> csrf
//                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                )
                .headers((headers) ->
                        headers
                                .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                ))
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .requestMatchers("/error**", "/favicon*", "/admin/assets/**", "/css/**", "/images/**", "/img/**", "/js/**", "/lib/**").permitAll()
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/", "/users/login", "/users/register").permitAll()
                                .requestMatchers("/about").permitAll()
                                .requestMatchers("/contacts").permitAll()
                                .requestMatchers("/products/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/admin/**").hasRole(RoleEnum.Administrator.name())
                                .requestMatchers(HttpMethod.POST, "/admin/**").hasRole(RoleEnum.Administrator.name())
                                .anyRequest().authenticated()


                ).formLogin(
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

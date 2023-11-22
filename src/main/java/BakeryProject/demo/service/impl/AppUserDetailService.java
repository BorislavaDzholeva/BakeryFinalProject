package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.entity.UserEntity;
import BakeryProject.demo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Stream;

public class AppUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public AppUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));

    }

    private UserDetails map(UserEntity userEntity) {
        return User.withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities
                        (Stream.of(userEntity.getRole().name()).map(this::mapToRole).toList()).build();
    }

    private GrantedAuthority mapToRole(String role) {
        return new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_" + role;
            }
        };
    }

}

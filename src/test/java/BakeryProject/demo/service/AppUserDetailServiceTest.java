package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.UserRegistrationDTO;
import BakeryProject.demo.models.entity.UserEntity;
import BakeryProject.demo.models.enums.RoleEnum;
import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.impl.AppUserDetailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppUserDetailServiceTest {
    private AppUserDetailService serviceToTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        serviceToTest = new AppUserDetailService(mockUserRepository);


    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            serviceToTest.loadUserByUsername("user");
        });
    }

    @Test
    void testUserFound() {
        UserEntity testUserEntity = createTestUser();

        when(mockUserRepository.findByUsername(testUserEntity.getUsername())).thenReturn(Optional.of(testUserEntity));

        UserDetails userDetails = serviceToTest.loadUserByUsername(testUserEntity.getUsername());

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(testUserEntity.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(1, userDetails.getAuthorities().size());
        Assertions.assertTrue(containsAuthority(userDetails, "ROLE_" + testUserEntity.getRole().name()));
    }

    private static UserEntity createTestUser() {
        return new UserEntity() {
            {
                setId(1L);
                setFirstName("firstName");
                setLastName("lastName");
                setPassword("1234567");
                setEmail("firstName@abv.bg");
                setUsername("user");
                setRole(RoleEnum.valueOf("User"));
            }
        };
    }

    private boolean containsAuthority(UserDetails userDetails, String authority) {
        return userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(authority));
    }


}

package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.AdminAddUserDTO;
import BakeryProject.demo.models.DTO.UserRegistrationDTO;
import BakeryProject.demo.models.entity.UserEntity;
import BakeryProject.demo.models.enums.RoleEnum;
import BakeryProject.demo.repository.CartRepository;
import BakeryProject.demo.repository.OrderRepository;
import BakeryProject.demo.repository.ReviewRepository;
import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private CartRepository mockCartRepository;

    @Mock
    private ReviewRepository mockReviewRepository;
    @Mock
    private OrderRepository mockOrderRepository;
    @Spy
    private ModelMapper modelMapper;
    @Captor
    private ArgumentCaptor<UserEntity> userEntityArgumentCaptor;
    private UserServiceImpl serviceToTest;
    private UserEntity createTestUser;
    private UserRegistrationDTO testUserRegistrationDTO;
    private AdminAddUserDTO testAdminAddUserDTO;

    @BeforeEach
    void setUp() {
        serviceToTest = new UserServiceImpl(mockUserRepository, mockCartRepository, mockReviewRepository, mockOrderRepository, modelMapper, mockPasswordEncoder);
        createTestUser = new UserEntity() {
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
        testUserRegistrationDTO = new UserRegistrationDTO() {
            {
                setFirstName("firstName");
                setLastName("lastName");
                setPassword("1234567");
                setEmail("userTest@abv.bg");
                setUsername("userTest");
            }
        };
        testAdminAddUserDTO = new AdminAddUserDTO() {
            {
                setFirstName("firstName");
                setLastName("lastName");
                setPassword("1234567");
                setEmail("userTest@abv.bg");
                setUsername("userTest");
            }
        };
    }

    @Test
    void testUserRegistered() {

        when(mockPasswordEncoder.encode(testUserRegistrationDTO.getPassword())).thenReturn("encodedPassword");

        serviceToTest.registerUser(testUserRegistrationDTO);
        Mockito.verify(mockUserRepository).save(userEntityArgumentCaptor.capture());
    }

    @Test
    void testFindAllUsers() {
        serviceToTest.getAllUsers();
        Assertions.assertEquals(0, serviceToTest.getAllUsers().size());
    }

    @Test
    void updateUser() {
        when(mockUserRepository.findById(testAdminAddUserDTO.getId())).thenReturn(Optional.of(createTestUser));
        serviceToTest.updateUser(testAdminAddUserDTO);
        Mockito.verify(mockUserRepository).save(userEntityArgumentCaptor.capture());
        Assertions.assertEquals(testAdminAddUserDTO.getUsername(), userEntityArgumentCaptor.getValue().getUsername());
    }

    @Test
    void testFindUserByUsername() {
        when(mockUserRepository.findByUsername(createTestUser.getUsername())).thenReturn(Optional.of(createTestUser));
        serviceToTest.findUserByUsername(createTestUser.getUsername());
        Mockito.verify(mockUserRepository).findByUsername(createTestUser.getUsername());
        Assertions.assertEquals(createTestUser.getUsername(), serviceToTest.findUserByUsername(createTestUser.getUsername()).getUsername());
    }

    @Test
    void testRemoveUserById() {
        serviceToTest.removeUserById(1L);
        Mockito.verify(mockUserRepository).deleteById(1L);
        Assertions.assertTrue(mockUserRepository.findById(1L).isEmpty());
    }

    @Test
    void testAddUser() {
        serviceToTest.addUser(testAdminAddUserDTO);
        Mockito.verify(mockUserRepository).save(userEntityArgumentCaptor.capture());
        UserEntity capturedUser = userEntityArgumentCaptor.getValue();

        Assertions.assertEquals(testAdminAddUserDTO.getUsername(), capturedUser.getUsername());
        Assertions.assertEquals(testAdminAddUserDTO.getFirstName(), capturedUser.getFirstName());
        Assertions.assertEquals(testAdminAddUserDTO.getLastName(), capturedUser.getLastName());
        Assertions.assertEquals(testAdminAddUserDTO.getEmail(), capturedUser.getEmail());

    }
    @Test
    void testFindUserById() {
        when(mockUserRepository.findById(createTestUser.getId())).thenReturn(Optional.of(createTestUser));
        serviceToTest.findUserById(createTestUser.getId());
        Mockito.verify(mockUserRepository).findById(createTestUser.getId());
        Assertions.assertEquals(createTestUser.getId(), serviceToTest.findUserById(createTestUser.getId()).getId());
    }

}




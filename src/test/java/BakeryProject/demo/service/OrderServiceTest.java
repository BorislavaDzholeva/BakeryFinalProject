package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.CreateOrderDTO;
import BakeryProject.demo.models.entity.*;
import BakeryProject.demo.models.enums.OrderStatusEnum;
import BakeryProject.demo.models.enums.RoleEnum;
import BakeryProject.demo.repository.CartItemRepository;
import BakeryProject.demo.repository.OrderRepository;
import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository mockOrderRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private CartItemRepository mockCartItemRepository;
    @Captor
    private ArgumentCaptor<Order> orderArgumentCaptor;
    private Order testOrder;
    private OrderItem testOrderItem;
    private UserEntity testUser;
    private Cart testCart;
    private CreateOrderDTO testCreateOrderDTO;
    private OrderServiceImpl serviceToTest;

    @BeforeEach
    void setUp() {
        serviceToTest = new OrderServiceImpl(mockOrderRepository, mockUserRepository, mockCartItemRepository);
        testCart = new Cart() {
            {
                setId(1L);
                setCartItems(new ArrayList<>());
                setOwner(testUser);
            }
        };


        testUser = new UserEntity() {
            {
                setId(1L);
                setFirstName("firstName");
                setLastName("lastName");
                setPassword("1234567");
                setEmail("firstName@abv.bg");
                setUsername("user");
                setRole(RoleEnum.valueOf("User"));
                setCart(testCart);
            }
        };
        testOrder = new Order() {
            {
                setId(1L);
                setTotalPrice(BigDecimal.valueOf(1.0));
                setOrderStatus(OrderStatusEnum.Pending);
                setCity("city");
                setAddress("address");
                setPhoneNumber("phone number");
                setUser(testUser);
                setOrderItems(new ArrayList<>());

            }
        };
        testCreateOrderDTO = new CreateOrderDTO() {
            {
                setCity("city");
                setAddress("address");
                setPhoneNumber("phone number");

            }
        };
        testOrderItem = new OrderItem() {
            {
                setId(1L);
                setProduct(null);
                setQuantity(1);
                setPrice(BigDecimal.valueOf(1.0));

            }
        };
    }


    @Test
    void testFindById() {
        when(mockOrderRepository.findById(testOrder.getId())).thenReturn(Optional.of(testOrder));
        serviceToTest.findById(testOrder.getId());
        Mockito.verify(mockOrderRepository).findById(testOrder.getId());
        Assertions.assertEquals(testOrder.getId(), serviceToTest.findById(testOrder.getId()).getId());


    }

    @Test
    void testGetAllOrders() {
        when(mockOrderRepository.findAll()).thenReturn(List.of(testOrder));
        serviceToTest.getAllOrders();
        Mockito.verify(mockOrderRepository).findAll();
        Assertions.assertEquals(1, serviceToTest.getAllOrders().size());

    }

    @Test
    public void testCreateOrder() {
        // Arrange
        String username = "testUser";
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setCity("city");
        createOrderDTO.setAddress("address");
        createOrderDTO.setPhoneNumber("phone number");

        UserEntity user = new UserEntity();
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setId(1L); // Set an id for the CartItem
        Product product = new Product();
        product.setName("product 1");
        product.setPrice(BigDecimal.valueOf(10));
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        user.setCart(cart);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        serviceToTest.createOrder(createOrderDTO, username);

        // Assert
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(mockOrderRepository, Mockito.times(1)).save(any(Order.class));
        Mockito.verify(mockUserRepository, Mockito.times(1)).save(any(UserEntity.class));
        Mockito.verify(mockCartItemRepository, Mockito.times(1)).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(1L, argumentCaptor.getValue().longValue());
    }
    @Test
    public void testCreateOrderWithNoUser() {
        // Arrange
        String username = "testUser";
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setCity("city");
        createOrderDTO.setAddress("address");
        createOrderDTO.setPhoneNumber("phone number");

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> serviceToTest.createOrder(createOrderDTO, username));
    }

    @Test
    public void testCreateOrderWithEmptyCart() {
        // Arrange
        String username = "testUser";
        CreateOrderDTO createOrderDTO = new CreateOrderDTO();
        createOrderDTO.setCity("city");
        createOrderDTO.setAddress("address");
        createOrderDTO.setPhoneNumber("phone number");

        UserEntity user = new UserEntity();
        Cart cart = new Cart();
        cart.setCartItems(new ArrayList<>());
        user.setCart(cart);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        serviceToTest.createOrder(createOrderDTO, username);

        // Assert
        Mockito.verify(mockOrderRepository, Mockito.times(1)).save(any(Order.class));
        Mockito.verify(mockUserRepository, Mockito.times(1)).save(any(UserEntity.class));
        Mockito.verify(mockCartItemRepository, Mockito.never()).deleteById(any(Long.class));
    }

    @Test
    public void testChangeStatusFromPendingToShipped() throws IllegalAccessException {
        // Arrange
        Order order = new Order();
        order.setOrderStatus(OrderStatusEnum.Pending);
        order.setUser(testUser);

        // Act
        serviceToTest.changeStatus(order);

        // Assert
        Mockito.verify(mockOrderRepository, Mockito.times(1)).save(order);
        Assertions.assertEquals(OrderStatusEnum.Shipped, order.getOrderStatus());
    }

    @Test
    public void testChangeStatusFromShipped() throws IllegalAccessException {
        // Arrange
        Order order = new Order();
        order.setOrderStatus(OrderStatusEnum.Shipped);
        order.setUser(testUser);

        when(mockUserRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));

        // Act
        serviceToTest.changeStatus(order);

        // Assert
        Mockito.verify(mockUserRepository, Mockito.times(1)).findById(testUser.getId());
    }

    @Test
    public void testChangeStatusFromShippedButUserNotFound() {
        // Arrange
        Order order = new Order();
        order.setOrderStatus(OrderStatusEnum.Shipped);
        order.setUser(testUser);

        when(mockUserRepository.findById(testUser.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalAccessException.class, () -> serviceToTest.changeStatus(order));
    }


}

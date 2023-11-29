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
    private ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
    private Order testOrder;
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

        String username = "testUser";

        testUser = new UserEntity();
        testCart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        Product product = new Product();
        product.setName("product 1");
        product.setPrice(BigDecimal.valueOf(10));
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        testCart.setCartItems(cartItems);
        testUser.setCart(testCart);
        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
        serviceToTest.createOrder(testCreateOrderDTO, username);


        Mockito.verify(mockOrderRepository, Mockito.times(1)).save(any(Order.class));
        Mockito.verify(mockUserRepository, Mockito.times(1)).save(any(UserEntity.class));
        Mockito.verify(mockCartItemRepository, Mockito.times(1)).deleteById(argumentCaptor.capture());
        Assertions.assertEquals(1L, argumentCaptor.getValue().longValue());
    }
    @Test
    public void testCreateOrderWithNoUser() {

        String username = "testUser";

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> serviceToTest.createOrder(testCreateOrderDTO, username));
    }

    @Test
    public void testCreateOrderWithEmptyCart() {

        String username = "testUser";

        testUser = new UserEntity();
        testCart = new Cart();
        testCart.setCartItems(new ArrayList<>());
        testUser.setCart(testCart);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
        serviceToTest.createOrder(testCreateOrderDTO, username);

        Mockito.verify(mockOrderRepository, Mockito.times(1)).save(any(Order.class));
        Mockito.verify(mockUserRepository, Mockito.times(1)).save(any(UserEntity.class));
        Mockito.verify(mockCartItemRepository, Mockito.never()).deleteById(any(Long.class));
    }

    @Test
    public void testChangeStatusFromPendingToShipped() throws IllegalAccessException {

        Order order = new Order();
        order.setOrderStatus(OrderStatusEnum.Pending);
        order.setUser(testUser);

        serviceToTest.changeStatus(order);

        Mockito.verify(mockOrderRepository, Mockito.times(1)).save(order);
        Assertions.assertEquals(OrderStatusEnum.Shipped, order.getOrderStatus());
    }

    @Test
    public void testChangeStatusFromShipped() throws IllegalAccessException {
        Order order = new Order();
        order.setOrderStatus(OrderStatusEnum.Shipped);
        order.setUser(testUser);

        when(mockUserRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));
        serviceToTest.changeStatus(order);

        Mockito.verify(mockUserRepository, Mockito.times(1)).findById(testUser.getId());
    }

    @Test
    public void testChangeStatusFromShippedButUserNotFound() {

        Order order = new Order();
        order.setOrderStatus(OrderStatusEnum.Shipped);
        order.setUser(testUser);

        when(mockUserRepository.findById(testUser.getId())).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalAccessException.class, () -> serviceToTest.changeStatus(order));
    }


}

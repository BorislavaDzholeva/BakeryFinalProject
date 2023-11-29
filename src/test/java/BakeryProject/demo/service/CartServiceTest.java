package BakeryProject.demo.service;

import BakeryProject.demo.models.entity.*;
import BakeryProject.demo.models.view.CartItemView;
import BakeryProject.demo.repository.CartItemRepository;
import BakeryProject.demo.repository.CartRepository;
import BakeryProject.demo.repository.ProductRepository;
import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.impl.CartServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private ProductRepository mockProductRepository;
    @Mock
    private CartRepository mockCartRepository;
    @Mock
    private CartItemRepository mockCartItemRepository;
    @Captor
    private ArgumentCaptor<Cart> cartArgumentCaptor;
    private CartServiceImpl serviceToTest;
    private UserEntity testUser;
    private Product testProduct;
    private Cart testCart;
    private CartItem testCartItem1;
    private CartItem testCartItem2;

    @BeforeEach
    void setUp() {
        serviceToTest = new CartServiceImpl(mockCartRepository, mockUserRepository, mockProductRepository, mockCartItemRepository);
    }

    @Test
    void testBuyProductById_NewCart() {
        Long productId = 1L;
        String currentUser = "username";

        testUser = new UserEntity();
        testProduct = new Product();
        testProduct.setId(productId);

        when(mockUserRepository.findByUsername(currentUser)).thenReturn(Optional.of(testUser));
        when(mockProductRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        serviceToTest.buyProductById(productId, currentUser);

        cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        Mockito.verify(mockCartRepository, times(2)).save(cartArgumentCaptor.capture());

        testCart = cartArgumentCaptor.getValue();
        Assertions.assertEquals(1, testCart.getCartItems().size());
        Assertions.assertEquals(1, testCart.getCartItems().get(0).getQuantity());
    }

    @Test
    void testBuyProductById_ExistingCart() {
        Long productId = 1L;
        String currentUser = "username";

        testUser = new UserEntity();
        testCart = new Cart();
        testCart.setCartItems(new ArrayList<>());
        testUser.setCart(testCart);

        testProduct = new Product();
        testProduct.setId(productId);

        testCartItem1 = new CartItem();
        testCartItem1.setProduct(testProduct);
        testCartItem1.setQuantity(1);
        testCart.getCartItems().add(testCartItem1);

        when(mockUserRepository.findByUsername(currentUser)).thenReturn(Optional.of(testUser));
        when(mockProductRepository.findById(productId)).thenReturn(Optional.of(testProduct));
        serviceToTest.buyProductById(productId, currentUser);

        cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        Mockito.verify(mockCartRepository, times(1)).save(cartArgumentCaptor.capture());

        Cart savedCart = cartArgumentCaptor.getValue();
        Assertions.assertEquals(1, savedCart.getCartItems().size());
        Assertions.assertEquals(2, savedCart.getCartItems().get(0).getQuantity());
    }

    @Test
    void testGetCartItems() {

        String currentUser = "username";

        testUser = new UserEntity();
        testCart = new Cart();
        testCart.setCartItems(new ArrayList<>());
        testUser.setCart(testCart);

        testCartItem1 = new CartItem();
        testCartItem1.setProduct(new Product());
        testCartItem1.getProduct().setName("Product 1");
        testCartItem1.getProduct().setPrice(BigDecimal.valueOf(10));
        testCartItem1.setQuantity(2);

        testCartItem2 = new CartItem();
        testCartItem2.setProduct(new Product());
        testCartItem2.getProduct().setName("Product 2");
        testCartItem2.getProduct().setPrice(BigDecimal.valueOf(5));
        testCartItem2.setQuantity(3);

        testCart.getCartItems().addAll(Arrays.asList(testCartItem1, testCartItem2));

        when(mockUserRepository.findByUsername(currentUser)).thenReturn(Optional.of(testUser));

        List<CartItemView> cartItems = serviceToTest.getCartItems(currentUser);

        Assertions.assertEquals(2, cartItems.size());
        Assertions.assertEquals("Product 1", cartItems.get(0).getName());
        Assertions.assertEquals(BigDecimal.valueOf(10), cartItems.get(0).getPrice());
        Assertions.assertEquals(2, cartItems.get(0).getQuantity());
        Assertions.assertEquals("Product 2", cartItems.get(1).getName());
        Assertions.assertEquals(BigDecimal.valueOf(5), cartItems.get(1).getPrice());
        Assertions.assertEquals(3, cartItems.get(1).getQuantity());
    }

    @Test
    void testGetTotalPrice() {

        String currentUser = "username";

        testUser = new UserEntity();
        testCart = new Cart();
        testCart.setCartItems(new ArrayList<>());
        testUser.setCart(testCart);

        testCartItem1 = new CartItem();
        testCartItem1.setProduct(new Product());
        testCartItem1.getProduct().setPrice(BigDecimal.valueOf(10));
        testCartItem1.setQuantity(2);

        testCartItem2 = new CartItem();
        testCartItem2.setProduct(new Product());
        testCartItem2.getProduct().setPrice(BigDecimal.valueOf(5));
        testCartItem2.setQuantity(3);

        testCart.getCartItems().addAll(Arrays.asList(testCartItem1, testCartItem2));

        when(mockUserRepository.findByUsername(currentUser)).thenReturn(Optional.of(testUser));

        BigDecimal totalPrice = serviceToTest.getTotalPrice(currentUser);
        Assertions.assertEquals(BigDecimal.valueOf(35), totalPrice);
    }

    @Test
    public void testRemoveProductFromCart() {
        String username = "username";
        Long id = 1L;

        testUser = new UserEntity();
        testCart = new Cart();
        testCartItem1 = new CartItem();
        testCartItem1.setId(id);
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(testCartItem1);
        testCart.setCartItems(cartItems);
        testUser.setCart(testCart);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
        serviceToTest.removeProductFromCart(username, id);

        Mockito.verify(mockCartRepository, times(1)).save(any(Cart.class));
        Mockito.verify(mockCartItemRepository, times(1)).delete(any(CartItem.class));
    }

    @Test
    public void testIncrementItem() {

        String username = "username";
        Long id = 1L;

        testUser = new UserEntity();
        testCart = new Cart();
        testCartItem1 = new CartItem();
        testCartItem1.setId(id);
        testCartItem1.setQuantity(1);
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(testCartItem1);
        testCart.setCartItems(cartItems);
        testUser.setCart(testCart);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
        serviceToTest.incrementItem(username, id);
        Mockito.verify(mockCartItemRepository, times(1)).save(any(CartItem.class));

    }

    @Test
    public void testDecrementItem_QuantityMoreThanOne() {
        String username = "username";
        Long id = 1L;

        testUser = new UserEntity();
        testCart = new Cart();
        testCartItem1 = new CartItem();
        testCartItem1.setId(id);
        testCartItem1.setQuantity(2);
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(testCartItem1);
        testCart.setCartItems(cartItems);
        testUser.setCart(testCart);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
        serviceToTest.decrementItem(username, id);

        Assertions.assertEquals(1, testCartItem1.getQuantity());
        Mockito.verify(mockCartItemRepository, times(1)).save(testCartItem1);
    }

    @Test
    public void testDecrementItem_QuantityEqualsOne() {

        String username = "username";
        Long id = 1L;

        testUser = new UserEntity();
        testCart = new Cart();
        testCartItem1 = new CartItem();
        testCartItem1.setId(id);
        testCartItem1.setQuantity(1);
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(testCartItem1);
        testCart.setCartItems(cartItems);
        testUser.setCart(testCart);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
        serviceToTest.decrementItem(username, id);

        Assertions.assertTrue(testCart.getCartItems().isEmpty());
        Mockito.verify(mockCartRepository, times(1)).save(testCart);
        Mockito.verify(mockCartItemRepository, times(1)).delete(testCartItem1);
    }
}
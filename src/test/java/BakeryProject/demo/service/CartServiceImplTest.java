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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @InjectMocks
    private CartServiceImpl cartService;
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

    private UserEntity testUser;
    private Product testProduct;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testBuyProductById_NewCart() {
        // Arrange
        Long productId = 1L;
        String currentUser = "username";

        testUser= new UserEntity();
        testProduct = new Product();
        testProduct.setId(productId);

        when(mockUserRepository.findByUsername(currentUser)).thenReturn(Optional.of(testUser));
        when(mockProductRepository.findById(productId)).thenReturn(Optional.of(testProduct));

        // Act
        cartService.buyProductById(productId, currentUser);

        // Assert
        cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(mockCartRepository, times(2)).save(cartArgumentCaptor.capture());

        Cart savedCart = cartArgumentCaptor.getValue();
        assertEquals(1, savedCart.getCartItems().size());
        assertEquals(1, savedCart.getCartItems().get(0).getQuantity());
    }

    @Test
    void testBuyProductById_ExistingCart() {
        // Arrange
        Long productId = 1L;
        String currentUser = "username";

        testUser = new UserEntity();
        Cart cart = new Cart();
        cart.setCartItems(new ArrayList<>());
        testUser.setCart(cart);

        Product product = new Product();
        product.setId(productId);

        CartItem existingItem = new CartItem();
        existingItem.setProduct(product);
        existingItem.setQuantity(1);
        cart.getCartItems().add(existingItem);

        when(mockUserRepository.findByUsername(currentUser)).thenReturn(Optional.of(testUser));
        when(mockProductRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        cartService.buyProductById(productId, currentUser);

        // Assert
        cartArgumentCaptor = ArgumentCaptor.forClass(Cart.class);
        verify(mockCartRepository, times(1)).save(cartArgumentCaptor.capture());

        Cart savedCart = cartArgumentCaptor.getValue();
        assertEquals(1, savedCart.getCartItems().size());
        assertEquals(2, savedCart.getCartItems().get(0).getQuantity());
    }

    @Test
    void testGetCartItems() {
        // Arrange
        String currentUser = "username";

        testUser = new UserEntity();
        Cart cart = new Cart();
        cart.setCartItems(new ArrayList<>());
        testUser.setCart(cart);

        CartItem item1 = new CartItem();
        item1.setProduct(new Product());
        item1.getProduct().setName("Product 1");
        item1.getProduct().setPrice(BigDecimal.valueOf(10));
        item1.setQuantity(2);

        CartItem item2 = new CartItem();
        item2.setProduct(new Product());
        item2.getProduct().setName("Product 2");
        item2.getProduct().setPrice(BigDecimal.valueOf(5));
        item2.setQuantity(3);

        cart.getCartItems().addAll(Arrays.asList(item1, item2));

        when(mockUserRepository.findByUsername(currentUser)).thenReturn(Optional.of(testUser));

        // Act
        List<CartItemView> cartItems = cartService.getCartItems(currentUser);

        // Assert
        assertEquals(2, cartItems.size());
        assertEquals("Product 1", cartItems.get(0).getName());
        assertEquals(BigDecimal.valueOf(10), cartItems.get(0).getPrice());
        assertEquals(2, cartItems.get(0).getQuantity());
        assertEquals("Product 2", cartItems.get(1).getName());
        assertEquals(BigDecimal.valueOf(5), cartItems.get(1).getPrice());
        assertEquals(3, cartItems.get(1).getQuantity());
    }

    @Test
    void testGetTotalPrice() {
        // Arrange
        String currentUser = "username";

        testUser = new UserEntity();
        Cart cart = new Cart();
        cart.setCartItems(new ArrayList<>());
        testUser.setCart(cart);

        CartItem item1 = new CartItem();
        item1.setProduct(new Product());
        item1.getProduct().setPrice(BigDecimal.valueOf(10));
        item1.setQuantity(2);

        CartItem item2 = new CartItem();
        item2.setProduct(new Product());
        item2.getProduct().setPrice(BigDecimal.valueOf(5));
        item2.setQuantity(3);

        cart.getCartItems().addAll(Arrays.asList(item1, item2));

        when(mockUserRepository.findByUsername(currentUser)).thenReturn(Optional.of(testUser));

        // Act
        BigDecimal totalPrice = cartService.getTotalPrice(currentUser);

        // Assert
        assertEquals(BigDecimal.valueOf(35), totalPrice);
    }

    @Test
    public void testRemoveProductFromCart() {
        // Arrange
        String username = "testUser";
        Long id = 1L;

        testUser = new UserEntity();
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setId(id);
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        testUser.setCart(cart);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(testUser));

        // Act
        cartService.removeProductFromCart(username, id);

        // Assert
        verify(mockCartRepository, times(1)).save(any(Cart.class));
        verify(mockCartItemRepository, times(1)).delete(any(CartItem.class));
    }

    @Test
    public void testIncrementItem() {
        // Arrange
        String username = "testUser";
        Long id = 1L;

        testUser = new UserEntity();
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setId(id);
        cartItem.setQuantity(1);
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        testUser.setCart(cart);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(testUser));

        // Act
        cartService.incrementItem(username, id);

        // Assert
        verify(mockCartItemRepository, times(1)).save(any(CartItem.class));
    }

    @Test
    public void testDecrementItem_QuantityMoreThanOne() {
        // Arrange
        String username = "testUser";
        Long id = 1L;

        testUser = new UserEntity();
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setId(id);
        cartItem.setQuantity(2);
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        testUser.setCart(cart);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(testUser));

        // Act
        cartService.decrementItem(username, id);

        // Assert
        assertEquals(1, cartItem.getQuantity());
        verify(mockCartItemRepository, times(1)).save(cartItem);
    }

    @Test
    public void testDecrementItem_QuantityEqualsOne() {
        // Arrange
        String username = "testUser";
        Long id = 1L;

        testUser = new UserEntity();
        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setId(id);
        cartItem.setQuantity(1);
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        cart.setCartItems(cartItems);
        testUser.setCart(cart);

        when(mockUserRepository.findByUsername(username)).thenReturn(Optional.of(testUser));

        // Act
        cartService.decrementItem(username, id);

        // Assert
        Assertions.assertTrue(cart.getCartItems().isEmpty());
        verify(mockCartRepository, times(1)).save(cart);
        verify(mockCartItemRepository, times(1)).delete(cartItem);
    }
}
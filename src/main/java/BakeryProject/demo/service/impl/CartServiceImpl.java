package BakeryProject.demo.service.impl;

import BakeryProject.demo.exception.ObjectNotFoundException;
import BakeryProject.demo.models.entity.Cart;
import BakeryProject.demo.models.entity.CartItem;
import BakeryProject.demo.models.entity.Product;
import BakeryProject.demo.models.entity.UserEntity;
import BakeryProject.demo.models.view.CartItemView;
import BakeryProject.demo.repository.CartItemRepository;
import BakeryProject.demo.repository.CartRepository;
import BakeryProject.demo.repository.ProductRepository;
import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.CartService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public void buyProductById(Long productId, String currentUser) {

        UserEntity user = userRepository.findByUsername(currentUser).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);

        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setOwner(user);
            cartRepository.save(cart);
        }
        List<CartItem> cartItems = cart.getCartItems();
        CartItem item = new CartItem();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getId().equals(productId)) {
                item = cartItem;
            }
        }
        if (item.getProduct() == null) {
            item.setProduct(product);
            item.setQuantity(1);
            cartItems.add(item);
        } else {
            item.setQuantity(item.getQuantity() + 1);
        }
        cart.setCartItems(cartItems);
        cartRepository.save(cart);
    }

    @Override
    public List<CartItemView> getCartItems(String currentUser) {
        UserEntity user = userRepository.findByUsername(currentUser).orElse(null);
        List<CartItem> cartItems = user.getCart().getCartItems();
        List<CartItemView> cartItemViews = cartItems.stream().map(cartItem -> {
            CartItemView cartItemView = new CartItemView();
            cartItemView.setId(cartItem.getId());
            cartItemView.setName(cartItem.getProduct().getName());
            cartItemView.setPrice(cartItem.getProduct().getPrice());
            cartItemView.setQuantity(cartItem.getQuantity());
            return cartItemView;
        }).toList();
        return cartItemViews;
    }

    @Override
    public BigDecimal getTotalPrice(String currentUser) {
        UserEntity user = userRepository.findByUsername(currentUser).orElse(null);
        List<CartItem> cartItems = user.getCart().getCartItems();
        return cartItems.stream().map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()))).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }


    @Override
    public void removeProductFromCart(String username, Long id) {
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        Cart cart = user.getCart();
        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItemToRemove = new CartItem();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getId().equals(id)) {
                cartItemToRemove = cartItem;
            }
        }
        cartItems.remove(cartItemToRemove);
        cart.setCartItems(cartItems);
        cartRepository.save(cart);
        cartItemRepository.delete(cartItemToRemove);
    }

    @Override
    public int incrementItem(String username, Long id) {
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        Cart cart = user.getCart();
        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItemToIncrement = new CartItem();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getId().equals(id)) {
                cartItemToIncrement = cartItem;
            }
        }
        int newCount = cartItemToIncrement.getQuantity() + 1;
        cartItemToIncrement.setQuantity(newCount);
        cartItemRepository.save(cartItemToIncrement);
        return newCount;
    }

    @Override
    public int decrementItem(String username, Long id) {
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        Cart cart = user.getCart();
        List<CartItem> cartItems = cart.getCartItems();
        CartItem cartItemToDecrement = new CartItem();
        for (CartItem cartItem : cartItems) {
            if (cartItem.getId().equals(id)) {
                cartItemToDecrement = cartItem;
            }
        }
        int newCount = cartItemToDecrement.getQuantity() - 1;
        cartItemToDecrement.setQuantity(newCount);
        if (cartItemToDecrement.getQuantity() == 0) {
            cartItems.remove(cartItemToDecrement);
            cart.setCartItems(cartItems);
            cartRepository.save(cart);
            cartItemRepository.delete(cartItemToDecrement);
            return 0;
        }
        cartItemRepository.save(cartItemToDecrement);
        return newCount;
    }
}

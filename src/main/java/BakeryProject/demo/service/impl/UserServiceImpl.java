package BakeryProject.demo.service.impl;

import BakeryProject.demo.exception.ObjectNotFoundException;
import BakeryProject.demo.models.DTO.AdminAddUserDTO;
import BakeryProject.demo.models.DTO.UserRegistrationDTO;
import BakeryProject.demo.models.entity.Cart;
import BakeryProject.demo.models.entity.Order;
import BakeryProject.demo.models.entity.UserEntity;
import BakeryProject.demo.models.view.UserView;
import BakeryProject.demo.repository.CartRepository;
import BakeryProject.demo.repository.OrderRepository;
import BakeryProject.demo.repository.ReviewRepository;
import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, CartRepository cartRepository, ReviewRepository reviewRepository, OrderRepository orderRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserView> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserView.class)).toList();
    }

    @Override
    public void removeUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Not found"));
        user.getUserReviews().forEach(review -> reviewRepository.deleteById(review.getId()));
        user.getUserOrders().forEach(order -> orderRepository.deleteById(order.getId()));
        cartRepository.deleteById(user.getCart().getId());
        userRepository.deleteById(id);
    }

    @Override
    public void addUser(AdminAddUserDTO addUserDTO) {
        UserEntity user = modelMapper.map(addUserDTO, UserEntity.class);
        user.setPassword(passwordEncoder.encode(addUserDTO.getPassword()));
        user = userRepository.save(user);
        Cart emptyCart = new Cart();
        emptyCart.setOwner(user);
        cartRepository.save(emptyCart);

    }

    @Override
    public AdminAddUserDTO findUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Not found"));
        return modelMapper.map(user, AdminAddUserDTO.class);
    }

    @Override
    public void updateUser(AdminAddUserDTO addUserDTO) {
        UserEntity user = userRepository.findById(addUserDTO.getId()).orElseThrow(() -> new ObjectNotFoundException("Not found"));
        user.setFirstName(addUserDTO.getFirstName());
        user.setLastName(addUserDTO.getLastName());
        user.setEmail(addUserDTO.getEmail());
        user.setUsername(addUserDTO.getUsername());
        user.setRole(addUserDTO.getRole());
        if (!addUserDTO.getPassword().isEmpty()) {
            user.setPassword(addUserDTO.getPassword());
        }
        userRepository.save(user);
    }

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        UserEntity user = modelMapper.map(userRegistrationDTO, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user = userRepository.save(user);
        Cart emptyCart = new Cart();
        emptyCart.setOwner(user);
        cartRepository.save(emptyCart);
    }


    @Override
    public UserEntity findUserByUsername(String currentUser) {
        return userRepository.findByUsername(currentUser).orElseThrow();
    }

    @Override
    public UserView getUserView(String name) {

        UserEntity userEntity = userRepository.findByUsername(name).orElseThrow();
        return modelMapper.map(userEntity, UserView.class);

    }
}

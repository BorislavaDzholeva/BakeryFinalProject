package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.DTO.AdminAddUserDTO;
import BakeryProject.demo.models.DTO.UserRegistrationDTO;
import BakeryProject.demo.models.entity.Cart;
import BakeryProject.demo.models.entity.UserEntity;
import BakeryProject.demo.models.view.UserView;
import BakeryProject.demo.repository.CartRepository;
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
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, CartRepository cartRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void removeUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void addUser(AdminAddUserDTO addUserDTO) {
        UserEntity user = modelMapper.map(addUserDTO, UserEntity.class);
        user = userRepository.save(user);
        Cart emptyCart = new Cart();
        emptyCart.setOwner(user);
        cartRepository.save(emptyCart);

    }

    @Override
    public AdminAddUserDTO findUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow();
        return modelMapper.map(user, AdminAddUserDTO.class);
    }

    @Override
    public void updateUser(AdminAddUserDTO addUserDTO) {
        UserEntity user = userRepository.findById(addUserDTO.getId()).orElseThrow();
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

package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.DTO.AdminAddUserDTO;
import BakeryProject.demo.models.DTO.UserRegistrationDTO;
import BakeryProject.demo.models.entity.UserEntity;
import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
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
        userRepository.save(user);
    }

    @Override
    public AdminAddUserDTO findUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        return modelMapper.map(user, AdminAddUserDTO.class);
    }

    @Override
    public void updateUser(AdminAddUserDTO addUserDTO) {
        UserEntity user = userRepository.findById(addUserDTO.getId()).orElse(null);
        if (user != null) {
            user.setFirstName(addUserDTO.getFirstName());
            user.setLastName(addUserDTO.getLastName());
            user.setAddress(addUserDTO.getAddress());
            user.setEmail(addUserDTO.getEmail());
            user.setUsername(addUserDTO.getUsername());
            user.setCity(addUserDTO.getCity());
            user.setPhoneNumber(addUserDTO.getPhoneNumber());
            user.setRole(addUserDTO.getRole());
            if (addUserDTO.getPassword().isEmpty()) {
                user.setPassword(addUserDTO.getPassword());
            }
            userRepository.save(user);
        }
    }

    @Override
    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        UserEntity user = new UserEntity();
        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setUsername(userRegistrationDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        userRepository.save(user);
    }
}

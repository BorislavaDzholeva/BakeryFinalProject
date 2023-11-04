package BakeryProject.demo.service.impl;

import BakeryProject.demo.models.DTO.AddUserDTO;
import BakeryProject.demo.models.entity.User;
import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void removeUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void addUser(AddUserDTO addUserDTO) {
        User user = modelMapper.map(addUserDTO, User.class);
        userRepository.save(user);
    }

    @Override
    public AddUserDTO findUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return modelMapper.map(user, AddUserDTO.class);
    }

    @Override
    public void updateUser(AddUserDTO addUserDTO) {
        User user = userRepository.findById(addUserDTO.getId()).orElse(null);
        if (user != null) {
            user.setFirstName(addUserDTO.getFirstName());
            user.setLastName(addUserDTO.getLastName());
            user.setAddress(addUserDTO.getAddress());
            user.setEmail(addUserDTO.getEmail());
            user.setCity(addUserDTO.getCity());
            user.setPhoneNumber(addUserDTO.getPhoneNumber());
            user.setRole(addUserDTO.getRole());
            if (addUserDTO.getPassword().isEmpty()) {
                user.setPassword(addUserDTO.getPassword());
            }
            userRepository.save(user);
        }
    }
}

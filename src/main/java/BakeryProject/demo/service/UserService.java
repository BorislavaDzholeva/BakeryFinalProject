package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.AdminAddUserDTO;
import BakeryProject.demo.models.DTO.UserRegistrationDTO;
import BakeryProject.demo.models.entity.UserEntity;

import java.util.List;

public interface UserService {
    public List<UserEntity> getAllUsers();

    void removeUserById(Long id);

    void addUser(AdminAddUserDTO addUserDTO);

    AdminAddUserDTO findUserById(Long id);

    void updateUser(AdminAddUserDTO addUserDTO);

    void registerUser(UserRegistrationDTO userRegistrationDTO);

}

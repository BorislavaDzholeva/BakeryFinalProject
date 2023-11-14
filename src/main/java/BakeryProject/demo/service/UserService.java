package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.AdminAddUserDTO;
import BakeryProject.demo.models.entity.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();

    void removeUserById(Long id);

    void addUser(AdminAddUserDTO addUserDTO);

    AdminAddUserDTO findUserById(Long id);

    void updateUser(AdminAddUserDTO addUserDTO);
}

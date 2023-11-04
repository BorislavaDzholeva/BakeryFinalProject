package BakeryProject.demo.service;

import BakeryProject.demo.models.DTO.AddUserDTO;
import BakeryProject.demo.models.entity.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers();

    void removeUserById(Long id);

    void addUser(AddUserDTO addUserDTO);

    AddUserDTO findUserById(Long id);

    void updateUser(AddUserDTO addUserDTO);
}

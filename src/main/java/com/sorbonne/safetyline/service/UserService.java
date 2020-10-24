package com.sorbonne.safetyline.service;

import com.sorbonne.safetyline.dataAccess.UsersRepository;
import com.sorbonne.safetyline.exception.DuplicateUsernameException;
import com.sorbonne.safetyline.model.User;

import java.util.List;

public class UserService {
    UsersRepository usersRepository = new UsersRepository();

    public List<User> getUsers() {
        return usersRepository.getList();
    }

    public User findUserById(String id_ticket)
    {
        return usersRepository.findById(id_ticket);
    }

    public User findUserByUserId(String user_id)
    {
        return usersRepository.findByEmail(user_id);
    }

    public void addOrUpdateUser(User newUser) throws DuplicateUsernameException
    {
        User existingUser = findUserByUserId(newUser.getIdUser());
        if (existingUser == null) {
            usersRepository.addItem(newUser);
        } else {
            existingUser.setId_ticket(newUser.getId_ticket());
            existingUser.setPassword(newUser.getPassword());
        }
    }
}

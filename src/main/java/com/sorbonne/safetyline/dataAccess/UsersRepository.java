package com.sorbonne.safetyline.dataAccess;

import com.sorbonne.safetyline.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersRepository {
    private List<User> users = new ArrayList<>();
    private static int index = 1;

    public List<User> getList() {
        return users;
    }
    public void addItem(User user) {
        user.setId_ticket(index++);
        users.add(user);
    }

    public User findById(String id_ticket) {
        for (User user : users)
        {
            if(user.getId_ticket().equals(id_ticket))
                return user;
        }
        return null;
    }

    public User findByEmail(String email) {
        for (User user: users)
        {
            if(user.getIdUser().equals(email)){
                return user;
            }
        }
        return null;
    }
}

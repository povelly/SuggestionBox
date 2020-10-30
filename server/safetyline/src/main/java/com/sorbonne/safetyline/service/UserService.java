package com.sorbonne.safetyline.service;

import com.sorbonne.safetyline.dataAccess.UserDoa;
import com.sorbonne.safetyline.model.Strawpoll;
import com.sorbonne.safetyline.model.Suggestion;
import com.sorbonne.safetyline.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserDoa userdoa;

    public List<User> getAllUsers()
    {
        return userdoa.findAll();
    }
    @Transactional
    public void deleteUserById_user(String id_user)
    {
        userdoa.deleteUserById_user(id_user);
    }

    public List<User> getAllAdmins()
    {
        return userdoa.getAllAdmins();
    }

    public List<Strawpoll> getAllChoicesUser(String id_user)
    {
        return userdoa.getAllChoicesUser(id_user);
    }
    public List<Suggestion> getAllSuggestions(String id_user)
    {
        return userdoa.getAllSuggestions(id_user);
    }
}

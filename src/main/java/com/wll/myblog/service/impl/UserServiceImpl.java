package com.wll.myblog.service.impl;

import com.wll.myblog.dao.UserRepository;
import com.wll.myblog.po.User;
import com.wll.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {

        User user = userRepository.findByUsernameAndPassword(username, password);

        return user;
    }
}

package com.wll.myblog.service.impl;

import com.wll.myblog.dao.UserRepository;
import com.wll.myblog.po.User;
import com.wll.myblog.service.UserService;
import com.wll.myblog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {

        return userRepository.findByUsernameAndPassword(username, MD5Utils.code(password) );
    }
}

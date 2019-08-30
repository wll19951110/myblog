package com.wll.myblog.service;

import com.wll.myblog.po.User;

public interface UserService {

    User checkUser(String username,String password);

}

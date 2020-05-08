package com.example.demo.dao;

import com.example.demo.entity.User;

import java.util.List;

// 인터페이스의 모든 메소드는 기본적으로 public이기 때문에 굳이 명시하지 않아도 됨
public interface IUserService {
    List<User> getUserList();
    User getUser(Integer id);

    User createUser(User newUser);
    User modifyUser(User modifyUser);
    User removeUser(Integer id);
}

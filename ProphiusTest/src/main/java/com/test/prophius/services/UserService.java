package com.test.prophius.services;


import com.test.prophius.dtos.requestbodies.UserDtoReq;
import com.test.prophius.entities.User;

import java.util.List;

public interface UserService {

    User save(UserDtoReq user);

    List<User> findAll();

    void delete(long id);

    User findOne(String username);

    User findById(Long id);

    User _save(User user);

    User createUpdateUser(UserDtoReq user);
}

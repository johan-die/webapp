package com.boot.services;

import com.boot.entities.User;

public interface UserService extends CRUDService<User> {

    User findByUsername(String username);

}

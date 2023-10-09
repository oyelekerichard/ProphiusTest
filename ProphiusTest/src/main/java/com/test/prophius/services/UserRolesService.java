package com.test.prophius.services;

import com.test.prophius.entities.UserRoles;

import java.util.List;

public interface UserRolesService {


    UserRoles addUserRole(UserRoles userRoles);

    UserRoles deleteUserRole(UserRoles userRoles);

    UserRoles getUserRoleByUsername(String username);

    List<UserRoles> getAllUserRoles();

    UserRoles updateUserRole(UserRoles userRoles);
}
package com.test.prophius.controllers;


import com.test.prophius.Utility;
import com.test.prophius.config.Constants;
import com.test.prophius.dtos.requestbodies.ActivateUserRequest;
import com.test.prophius.dtos.requestbodies.UserDtoReq;
import com.test.prophius.dtos.responsebodies.ApiResponse;
import com.test.prophius.entities.User;
import com.test.prophius.entities.UserRoles;
import com.test.prophius.enums.Status;
import com.test.prophius.repositories.UserRepository;
import com.test.prophius.serviceimpl.UserRolesServiceImpl;
import com.test.prophius.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(
        origins = {"*"},
        maxAge = 3600L
)
@RestController
@RequestMapping({"/user"})
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    UserRolesServiceImpl userRolesService;
    @Autowired
    private UserRepository userDao;

    public UserController() {
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"/users"}, method = {RequestMethod.GET})
    @CrossOrigin
    public ApiResponse<User> listUser() {
        Status status = Status.SUCCESS;
        List<User> userList = this.userService.findAll();
        status = Status.SUCCESS;
        return new ApiResponse(status.getStatusCode(), status.getStatusMessage(), userList);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = {"/userid/{id}"}, method = {RequestMethod.GET})
    @CrossOrigin
    public ApiResponse<User> getOne(@PathVariable("id") Long id) {
        Status status = Status.SUCCESS;
        if (id < 0L) {
            status = Status.BAD_REQUEST;
            return new ApiResponse(status.getStatusCode(), status.getStatusMessage(), (Object) null);
        } else {
            User user = this.userService.findById(id);
            return new ApiResponse(status.getStatusCode(), status.getStatusMessage(), user);
        }
    }

    @RequestMapping(value = {"/username/{username}"}, method = {RequestMethod.GET})
    @CrossOrigin
    public ApiResponse<User> getByUsername(@PathVariable("username") String username) {
        int status = Status.BAD_REQUEST.getStatusCode();
        String message = Status.BAD_REQUEST.getStatusMessage();
        if (username.isEmpty()) {
            System.err.println("Username is empty");
            message = "Username is empty";
            return new ApiResponse(status, message, (Object) null);
        } else {
            User user = this.userService.findOne(username);
            Optional<User> userOptional = Optional.ofNullable(user);
            if (!userOptional.isPresent()) {
                status = Status.INVALID_USERNAME.getStatusCode();
                message = "User was NOT successfully fetched!";
                return new ApiResponse(status, message, (Object) null);
            } else {
                status = Status.SUCCESS.getStatusCode();
                message = "User was successfully fetched!";
                return new ApiResponse(status, message, user);
            }
        }
    }

    @RequestMapping(value = {"/create-update"}, method = {RequestMethod.POST})
    @CrossOrigin
    public ApiResponse<User> createUpdate(@RequestBody UserDtoReq user) {
        int status = Status.SUCCESS.getStatusCode();
        String message = "Success";
        if (!Utility.isValidEmail(user.getEmail())) {
            status = Status.INVALID_EMAIL.getStatusCode();
            message = "Invalid Email";
            return new ApiResponse(status, message, (Object) null);
        } else if (user.getUsername().isEmpty()) {
            status = Status.INVALID_USERNAME.getStatusCode();
            message = "Invalid Username";
            return new ApiResponse(status, message, (Object) null);
        } else {
            User u = this.userService.createUpdateUser(user);
            return new ApiResponse(status, message, u);
        }
    }

    @RequestMapping(value = {"/register"}, method = {RequestMethod.POST})
    @CrossOrigin
    public ApiResponse<User> saveUser(@RequestBody UserDtoReq user) {
        Status status = Status.SUCCESS;
        boolean hasErrors = false;
        String message = "";
        Optional<UserDtoReq> userOptional = Optional.ofNullable(user);
        Optional<User> uOptional = Optional.ofNullable(this.userService.findOne(user.getUsername()));
        User usr = null;
        if (uOptional.isPresent()) {
            status = Status.USER_EXISTS;
            hasErrors = true;
            message = "User Already Exists";
            return new ApiResponse(status.getStatusCode(), message, (Object) null);
        } else if (!Utility.isValidEmail(user.getEmail())) {
            status = Status.INVALID_EMAIL;
            hasErrors = true;
            message = "Invalid Email";
            return new ApiResponse(status.getStatusCode(), message, (Object) null);
        } else if (((UserDtoReq) userOptional.get()).getUsername().isEmpty()) {
            status = Status.INVALID_USERNAME;
            hasErrors = true;
            message = "Invalid Username";
            return new ApiResponse(status.getStatusCode(), message, (Object) null);
        } else {
            if (!hasErrors) {
                status = Status.SUCCESS;
                user.setActive(true);
                usr = userService.save(user);
                String var10000 = status.getStatusMessage();
                message = var10000 + "! " + usr.getUsername() + " was successfully registered!";
                User u = this.userService.findOne(user.getUsername());
                this.userRolesService.addUserRole(new UserRoles(u.getId(), Constants.ROLE_MAKER, u.getUsername(), "ROLE_MAKER", true));
            }

            return new ApiResponse(status.getStatusCode(), message, usr);
        }
    }

    @RequestMapping(
            value = {"/update"},
            method = {RequestMethod.POST}
    )
    @CrossOrigin
    public ApiResponse<User> updateUser(@RequestBody UserDtoReq user) {
        Status status = Status.SUCCESS;
        String message = "";
        Optional<User> uOptional = Optional.ofNullable(this.userService.findOne(user.getUsername()));
        new User();
        User usr = (User) uOptional.get();
        usr.setPassword(user.getPassword());
        usr.setUsername(user.getUsername());
        usr = this.userService._save(usr);
        String var10000 = status.getStatusMessage();
        message = var10000 + "! " + usr.getUsername() + " was successfully updated!";
        return new ApiResponse(status.getStatusCode(), message, usr);
    }

    @RequestMapping(
            value = {"/activate-deacticate-user"},
            method = {RequestMethod.POST}
    )
    @CrossOrigin
    public ApiResponse<User> activateDeactivateUser(@RequestBody ActivateUserRequest user) {
        Status status = Status.SUCCESS;
        String message = "";
        Optional<User> uOptional = Optional.ofNullable(this.userDao.findByUsername(user.getUsername()));
        new User();
        User usr = (User) uOptional.get();
        usr.setActive(user.isActive());
        usr = (User) this.userDao.save(usr);
        UserRoles userRole = this.userRolesService.getUserRoleByUsername(user.getUsername());
        userRole.setActive(user.isActive());
        this.userRolesService.addUserRole(userRole);
        String var10000;
        if (user.isActive()) {
            var10000 = status.getStatusMessage();
            message = var10000 + "! " + usr.getUsername() + " was successfully activated!";
        }

        if (!user.isActive()) {
            var10000 = status.getStatusMessage();
            message = var10000 + "! " + usr.getUsername() + " was successfully deactivated!";
        }

        return new ApiResponse(status.getStatusCode(), message, usr);
    }

    @RequestMapping(
            value = {"/user-status"},
            method = {RequestMethod.GET}
    )
    @CrossOrigin
    public ApiResponse<User> userStatus(@RequestParam String username) {
        Status status = Status.SUCCESS;
        String message = "";
        Optional<UserRoles> uOptional = Optional.ofNullable(this.userRolesService.getUserRoleByUsername(username));
        new UserRoles();
        UserRoles usr = (UserRoles) uOptional.get();
        return new ApiResponse(status.getStatusCode(), message, usr.isActive());
    }
}

package com.test.prophius.controllers;

import com.google.gson.Gson;
import com.test.prophius.dtos.requestbodies.UpdateUserRoleRequest;
import com.test.prophius.dtos.responsebodies.ApiResponse;
import com.test.prophius.entities.Role;
import com.test.prophius.entities.User;
import com.test.prophius.entities.UserRoles;
import com.test.prophius.enums.Status;
import com.test.prophius.repositories.RolesRepository;
import com.test.prophius.serviceimpl.UserRolesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"*"}, maxAge = 3600L)

@RestController
@RequestMapping({"/roles"})
public class RolesController {
    @Autowired
    UserRolesServiceImpl userRolesService;
    @Autowired
    RolesRepository roleRepository;

    public RolesController() {
    }

    @RequestMapping(value = {"/userrole/{username}"}, method = {RequestMethod.GET})
    @CrossOrigin
    public ApiResponse<User> getUserRole(@PathVariable("username") String username) {
        int status = Status.BAD_REQUEST.getStatusCode();
        String message = Status.BAD_REQUEST.getStatusMessage();
        if (username.isEmpty()) {
            System.err.println("Username is empty");
            message = "Username is empty";
            return new ApiResponse(status, message, (Object) null);
        } else {
            UserRoles userRole = this.userRolesService.getUserRoleByUsername(username);
            Optional<UserRoles> userRolesOptional = Optional.ofNullable(userRole);
            System.err.println("USER ROLE" + userRole);
            if (!userRolesOptional.isPresent()) {
                status = Status.INVALID_USERNAME.getStatusCode();
                message = "User role was NOT successfully fetched!";
                return new ApiResponse(status, message, (Object) null);
            } else {
                status = Status.SUCCESS.getStatusCode();
                message = "User role was successfully fetched!";
                return new ApiResponse(status, message, userRole);
            }
        }
    }

    @RequestMapping(value = {"/userrole/update"}, method = {RequestMethod.POST})
    @CrossOrigin
    public ApiResponse<UserRoles> updateUserRole(@RequestBody UpdateUserRoleRequest updateUserRoleReq) {
        Gson gson = new Gson();
        String pload = gson.toJson(updateUserRoleReq);
        System.err.println("PLOAD " + pload);
        System.err.println(updateUserRoleReq.getUsername());
        int status = Status.BAD_REQUEST.getStatusCode();
        String message = Status.BAD_REQUEST.getStatusMessage();
        if (updateUserRoleReq.getRolename().isEmpty()) {
            message = "Role name is empty";
            return new ApiResponse(status, message, (Object) null);
        } else if (updateUserRoleReq.getUsername().isEmpty()) {
            message = "Username is empty";
            return new ApiResponse(status, message, (Object) null);
        } else {
            UserRoles userRole = this.userRolesService.getUserRoleByUsername(updateUserRoleReq.getUsername());
            System.err.println("USER ROLE ------ " + userRole);
            System.err.println("ROLE NAME " + updateUserRoleReq.getRolename());
            List<Role> role = this.roleRepository.listRoleByName(updateUserRoleReq.getRolename());
            System.err.println("ROLE SIZE " + role.size());
            if (role.size() <= 0) {
                message = "Role does not exist";
            }

            System.err.println(((Role) role.get(0)).getId());
            userRole.setRoleId(((Role) role.get(0)).getId());
            userRole.setRolename(((Role) role.get(0)).getName());
            this.userRolesService.updateUserRole(userRole);
            status = Status.SUCCESS.getStatusCode();
            message = "User role was successfully updated!";
            return new ApiResponse(status, message, userRole);
        }
    }

    @RequestMapping(value = {"/userrole/all"}, method = {RequestMethod.GET})
    @CrossOrigin
    public ApiResponse<User> getAllUserRole() {
        int status = Status.BAD_REQUEST.getStatusCode();
        String message = Status.BAD_REQUEST.getStatusMessage();
        List<UserRoles> userRolesList = this.userRolesService.getAllUserRoles();
        if (userRolesList.size() <= 0) {
            status = Status.SUCCESS.getStatusCode();
            message = "Record is empty";
        }

        if (userRolesList.size() <= 0) {
            status = Status.SUCCESS.getStatusCode();
            message = "Success";
        }

        return new ApiResponse(status, message, userRolesList);
    }
}

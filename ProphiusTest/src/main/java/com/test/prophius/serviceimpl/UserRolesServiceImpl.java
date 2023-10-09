package com.test.prophius.serviceimpl;


import com.test.prophius.entities.UserRoles;
import com.test.prophius.repositories.UserRolesRepository;
import com.test.prophius.services.UserRolesService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRolesServiceImpl implements UserRolesService {


    UserRolesRepository userRolesRepository;


    //@Autowired
    //private UserRepository userRepository;

    public UserRolesServiceImpl(@Lazy UserRolesRepository userRolesRepository) {
        super();
        this.userRolesRepository = userRolesRepository;
    }


    @Override
    public UserRoles addUserRole(UserRoles userRoles) {

        UserRoles uRoles = null;
        Optional<UserRoles> urOptional = Optional.ofNullable(userRoles);

        if (urOptional.isPresent()) {
            uRoles = userRolesRepository.save(userRoles);
        }

        return uRoles;

    }

    @Override
    public UserRoles updateUserRole(UserRoles userRoles) {

        UserRoles uRoles = null;
        Optional<UserRoles> urOptional = Optional.ofNullable(userRoles);

        if (urOptional.isPresent()) {
            List<UserRoles> userRolez = userRolesRepository.getUserRoleByUsername(userRoles.getUsername());
            uRoles = userRolez.get(0);
            uRoles.setRoleId(userRoles.getRoleId());
            uRoles.setRolename(userRoles.getRolename());
            uRoles = userRolesRepository.save(uRoles);
        }

        return uRoles;

    }

    @Override
    public UserRoles deleteUserRole(UserRoles userRoles) {

        UserRoles uRoles = null;
        Optional<UserRoles> urOptional = Optional.ofNullable(userRoles);

        if (urOptional.isPresent()) {
            userRolesRepository.deleteUserRoleByUserIdByRoleId(userRoles.getRoleId(), userRoles.getUserId());
        }
        return uRoles;
    }

    @Override
    public UserRoles getUserRoleByUsername(String username) {

        List<UserRoles> userRolesList = null;
        UserRoles userRoles = null;
        Optional<String> userRoleOptional = Optional.ofNullable(username);

        if (userRoleOptional.isPresent()) {
            userRolesList = userRolesRepository.getUserRoleByUsername(username);
        }

        if (userRolesList.size() > 0) {
            userRoles = userRolesList.get(0);
        }

        System.err.println("USER ROLE " + userRoles);

        return userRoles;

    }

    @Override
    public List<UserRoles> getAllUserRoles() {
        List<UserRoles> listUserRoles = userRolesRepository.findAll();
        return listUserRoles;
    }
}


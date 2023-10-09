package com.test.prophius.serviceimpl;

import com.test.prophius.config.Constants;
import com.test.prophius.dtos.requestbodies.UserDtoReq;
import com.test.prophius.entities.UserRoles;
import com.test.prophius.repositories.UserRepository;
import com.test.prophius.services.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.test.prophius.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

    private UserRepository userDao;
    private UserService userService;
    UserRolesServiceImpl userRolesService;
    private BCryptPasswordEncoder bcryptEncoder;

    public UserServiceImpl(@Lazy UserRepository userDao, @Lazy UserService userService, @Lazy UserRolesServiceImpl userRolesService,
                           BCryptPasswordEncoder bcryptEncoder) {
        this.userDao = userDao;
        this.userService = userService;
        this.userRolesService = userRolesService;
        this.bcryptEncoder = bcryptEncoder;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userDao.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public void delete(long id) {
        userDao.deleteById(id);
    }

    @Override
    public User findOne(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).get();
    }

    @Override
    public User save(UserDtoReq user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDao.save(newUser);
    }

    @Override
    public User createUpdateUser(UserDtoReq user) {
        // Check if the user exists
        Optional<UserDtoReq> userOptional = Optional.ofNullable(user);
        Optional<User> uOptional = Optional.ofNullable(userDao.findByUsername(user.getUsername()));
        User usr = new User();

        if (uOptional.isPresent()) {
            // Update User
            System.err.println("Updating USER");
            User uzer = uOptional.get();
            uzer.setPassword(bcryptEncoder.encode(user.getPassword()));
            usr = userDao.save(uzer);
        }

        if (!uOptional.isPresent()) {
            // Create user
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
            usr = userDao.save(newUser);

            User u = userService.findOne(usr.getUsername());
            userRolesService.addUserRole(new UserRoles(u.getId(), Constants.USER_ROLE, u.getUsername(), "ROLE_MAKER", u.isActive()));
        }

        return usr;
    }

    @Override
    public User _save(User user) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return userDao.save(newUser);
    }
}

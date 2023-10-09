package com.test.prophius.repositories;

import com.test.prophius.entities.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {

    @Query(value = "SELECT * FROM user_roles WHERE user_id = ?1", nativeQuery = true)
    List<UserRoles> getUserRolesByUserId(Long userId);

    @Query(value = "SELECT * FROM user_roles WHERE role_id = ?1 AND user_id = ?2", nativeQuery = true)
    List<UserRoles> getUserRoleByRoleId(Long roleId, Long userId);

    @Query(value = "SELECT * FROM user_roles WHERE username = ?1", nativeQuery = true)
    List<UserRoles> getUserRoleByUsername(String username);

    List<UserRoles> findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "delete from user_roles  WHERE role_id = ?1 AND user_id = ?2", nativeQuery = true)
    void deleteUserRoleByUserIdByRoleId(Long roleId, Long userId);


}

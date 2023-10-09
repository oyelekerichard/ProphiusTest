package com.test.prophius.repositories;

import com.test.prophius.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT * FROM role where name = ?1", nativeQuery = true)
    public List<Role> listRoleByName(String name);

}

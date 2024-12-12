package com.api.shopan.repositories;

import com.api.shopan.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select role from Role role where role.id = ?1")
    Role findOne(Integer id);
}
package com.example.moduleone.reporsitories;

import com.example.moduleone.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByRole(String role);
}

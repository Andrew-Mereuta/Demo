package com.example.moduleone.reporsitories;

import com.example.moduleone.models.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findUserEntityByNetid(String netid);
}

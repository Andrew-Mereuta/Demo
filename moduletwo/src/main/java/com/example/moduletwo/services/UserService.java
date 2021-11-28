package com.example.moduletwo.services;

import com.example.moduletwo.database.Database;
import com.example.moduletwo.models.Role;
import com.example.moduletwo.models.UserEntity;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class UserService {

    private final Database database = new Database("jdbc:postgresql://localhost:9095/demo", "admin", "password");

    public UserEntity getUser(Long id) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement statement = connection.prepareStatement("select role.role as role, app_user.netid from role, app_user_roles, app_user where role.id=app_user_roles.roles_id and app_user_roles.user_entity_id=app_user.id and app_user.id=?");
        statement.setInt(1, id.intValue());
        ResultSet rs = statement.executeQuery();
        UserEntity userEntity = new UserEntity();
        if (rs.next()) {
            userEntity.setNetid(rs.getString("netid"));
            userEntity.addRole(new Role(rs.getString("role")));
        }
        while(rs.next()) {
            userEntity.addRole(new Role(rs.getString("role")));
        }

        return userEntity;
    }
}
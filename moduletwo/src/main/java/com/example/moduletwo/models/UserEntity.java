package com.example.moduletwo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserEntity {

    @JsonIgnore
    private Long id;

    private String netid;

    @JsonIgnore
    private String password;

    private Collection<Role> roles = new ArrayList<>();

    public UserEntity(String netid, String password, Collection<Role> roles){
        this.netid = netid;
        this.password = password;
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }
}


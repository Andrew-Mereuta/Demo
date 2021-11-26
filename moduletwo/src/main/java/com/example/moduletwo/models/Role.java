package com.example.moduletwo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Role {

    @JsonIgnore
    private Long id;
    private String role;

    public Role(String role){
        this.role = role;
    }
}
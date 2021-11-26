package com.example.moduleone.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class UserRequest {
    private String netid;
    private String password;
}

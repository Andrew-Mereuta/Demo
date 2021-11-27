package com.example.moduleone.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@Table(name="app_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String netid;

    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Course> currentCourses = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Course> taCourses = new HashSet<>();


    public UserEntity(String netid, String password, Set<Course> currentCourses, Set<Course> taCourses){
        this.netid = netid;
        this.password = password;
        this.currentCourses = new HashSet<>(currentCourses);
        this.taCourses = new HashSet<>(taCourses);
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        this.currentCourses.forEach(course -> {
            authorities.add(new SimpleGrantedAuthority(course.getCourseCode() + "_STUDENT"));
        });
        this.taCourses.forEach(course -> {
            authorities.add(new SimpleGrantedAuthority(course.getCourseCode() + "_TA"));
        });

        return authorities;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return netid;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}

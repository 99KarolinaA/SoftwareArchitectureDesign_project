package mk.ukim.finki.dians.parking_application.model;

import lombok.Data;
import mk.ukim.finki.dians.parking_application.model.enumeration.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

/**
 * Class for the User Entity
 * Contains all of the entity fields and their
 * declaration, the class constructor and/or setters/getters.
 * The class implements UserDetails
 * which provides user information.
 */
@Data
@Entity
@Table(name = "parking_users")
public class User implements UserDetails {

    @Id
    private String username;
    private String password;
    private String name;
    private String surname;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @Enumerated(value =  EnumType.STRING)
    private Role role;

    public User() {

    }

    public User(String username, String password, String name, String surname) {

        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = Role.ROLE_USER;
    }

    public User(String username, String password, String name, String surname, Role role) {

        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}

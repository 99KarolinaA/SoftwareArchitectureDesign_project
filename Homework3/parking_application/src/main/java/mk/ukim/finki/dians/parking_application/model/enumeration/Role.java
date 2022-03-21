package mk.ukim.finki.dians.parking_application.model.enumeration;

import org.springframework.security.core.GrantedAuthority;

/**
 * This Role enumeration is actually a permission.
 * Two permissions: USER and ADMIN.
 */
public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

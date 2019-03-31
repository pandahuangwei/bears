package com.bear.common.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-25 16:25.
 */
@Getter
@Setter
public class LoginAppUser extends AppUser implements UserDetails {

    private static final long serialVersionUID = 1753977564987556640L;

    private Set<Role> roles;

    private Set<String> permissions;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new HashSet<>();
        if (!CollectionUtils.isEmpty(roles)) {
            roles.forEach(role -> {
                if (role.getCode().startsWith("ROLE_")) {
                    collection.add(new SimpleGrantedAuthority(role.getCode()));
                } else {
                    collection.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
                }
            });
        }

        if (!CollectionUtils.isEmpty(permissions)) {
            permissions.forEach(per -> {
                collection.add(new SimpleGrantedAuthority(per));
            });
        }

        return collection;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getEnabled();
    }
}

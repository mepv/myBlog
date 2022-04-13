package me.mepv.blog.security;

import me.mepv.blog.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.Collection;
import java.util.Set;

public class AppUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Boolean accountNonExpired;
    private final Boolean accountNonLocked;
    private final Boolean credentialsNonExpired;
    private final Boolean enabled;
    @ElementCollection(targetClass = SimpleGrantedAuthority.class, fetch = FetchType.EAGER)
    private Set<? extends GrantedAuthority> grantedAuthorities;

    public AppUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.accountNonExpired = isAccountNonExpired();
        this.accountNonLocked = isAccountNonLocked();
        this.credentialsNonExpired = isCredentialsNonExpired();
        this.enabled = isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    public void setGrantedAuthorities(Set<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }
}

package demo.stackoverflow.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticationObject implements Authentication {

    private String userName;
    private boolean authenticated;

    public AuthenticationObject(String userName) {
        this.userName = userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userName;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated = b;
    }

    @Override
    public String getName() {
        return userName;
    }
}

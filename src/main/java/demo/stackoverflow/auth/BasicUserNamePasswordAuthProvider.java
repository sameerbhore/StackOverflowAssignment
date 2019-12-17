package demo.stackoverflow.auth;

import demo.stackoverflow.config.SecurityConfig;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class BasicUserNamePasswordAuthProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken cred = (UsernamePasswordAuthenticationToken) authentication;
        if(!cred.getPrincipal().equals(cred.getCredentials())) {
            throw new AuthenticationServiceException("Invalid Credentials");
        }
        Authentication authenticationObject = new AuthenticationObject((String) cred.getPrincipal());
        authenticationObject.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authenticationObject);
        return authenticationObject;
    }

    @Override
    public boolean supports(Class<?> aClass) {
       return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}

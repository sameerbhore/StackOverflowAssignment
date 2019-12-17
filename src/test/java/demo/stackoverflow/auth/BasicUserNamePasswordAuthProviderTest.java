package demo.stackoverflow.auth;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class BasicUserNamePasswordAuthProviderTest {

    @Test
    public void validateCorrectAuth(){
        BasicUserNamePasswordAuthProvider provider = new BasicUserNamePasswordAuthProvider();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("U1", "U1");
        Authentication authenticate = provider.authenticate(token);
        Assert.assertThat(authenticate.isAuthenticated(), Is.is(true));
    }

    @Test(expected = AuthenticationException.class)
    public void validateInCorrectAuth(){
        BasicUserNamePasswordAuthProvider provider = new BasicUserNamePasswordAuthProvider();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("U1", "U11");
        Authentication authenticate = provider.authenticate(token);
    }

    @Test
    public void validateUserNameSetAfterCorrectAuth(){
        BasicUserNamePasswordAuthProvider provider = new BasicUserNamePasswordAuthProvider();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("U1", "U1");
        Authentication authenticate = provider.authenticate(token);
        Assert.assertThat(authenticate.getPrincipal(), Is.is("U1"));
    }
}

package demo.stackoverflow.config;

import demo.stackoverflow.auth.BasicUserNamePasswordAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private BasicUserNamePasswordAuthProvider provider;

    @Autowired
    public SecurityConfig(BasicUserNamePasswordAuthProvider provider) {
        this.provider = provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .cors().disable()
            .authorizeRequests().anyRequest().authenticated().and().httpBasic().and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);

    }

}

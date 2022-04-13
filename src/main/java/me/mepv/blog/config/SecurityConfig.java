package me.mepv.blog.config;

import me.mepv.blog.security.AppUserDetailsServiceImpl;
import me.mepv.blog.security.jwt.JwtTokenVerifier;
import me.mepv.blog.security.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import static me.mepv.blog.security.AppUserPermission.USER_READ;
import static me.mepv.blog.security.AppUserPermission.USER_WRITE;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final AppUserDetailsServiceImpl appUserDetailsService;

    public SecurityConfig(PasswordEncoder passwordEncoder, AppUserDetailsServiceImpl appUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.appUserDetailsService = appUserDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/v1/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/posts").hasAuthority(USER_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/api/v1/posts").hasAuthority(USER_READ.getPermission())
                .anyRequest()
                .authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(appUserDetailsService);
        return provider;
    }
}

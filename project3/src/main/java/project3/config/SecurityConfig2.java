package project3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project3.filter.JwtAuthenticationFilter;
import project3.security.JwtTokenUtil;
import project3.service.UserDetailsServiceImpl;

import java.util.Collections;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig2 extends WebSecurityConfigurerAdapter {
    private UserDetailsServiceImpl userDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public SecurityConfig2(UserDetailsServiceImpl userDetailsService, JwtTokenUtil jwtTokenUtil){
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF protection if needed
                .authorizeRequests()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/registration/**").hasRole("ADMIN")
                .antMatchers("/student/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(getJwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsServiceImpl userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(Collections.singletonList(provider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public JwtAuthenticationFilter getJwtAuthenticationFilter(){
        return new JwtAuthenticationFilter(jwtTokenUtil, userDetailsService);
    }

}

package project3.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import project3.security.JwtTokenUtil;
import project3.service.UserDetailsServiceImpl;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtTokenUtil jwtTokenUtil;
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil, UserDetailsServiceImpl userDetailsService){
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String authHeader = request.getHeader("Authorization");

        //if no header or no token, continue filter
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Remove "Bearer " prefix

            try {
                String username = jwtTokenUtil.getUserNameFromToken(token);
                // check if the user has already been authenticated
                if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    //use userDetails to extract roles
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if(jwtTokenUtil.isTokenValid(token)){
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        username, null,
                                        userDetails.getAuthorities()
                                );

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        chain.doFilter(request, response);
    }
}
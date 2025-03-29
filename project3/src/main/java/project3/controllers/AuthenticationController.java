package project3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project3.models.pojo.JwtResponse;
import project3.models.pojo.LoginRequest;
import project3.security.JwtTokenUtil;

@RestController
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    //private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil ){
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping ("/auth/login")
    public ResponseEntity<?> userLogin(@RequestBody LoginRequest loginRequest){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword())
            );
            //manually save authenticatio in thread local so to that
            // Spring Security to recognize the user throughout the request lifecycle
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails user = (UserDetails) authentication.getPrincipal();
            String role = user.getAuthorities().stream()
                    .findFirst()
                    .map(e -> e.getAuthority())
                    .orElse(null);

            String token = jwtTokenUtil.generateToken(user.getUsername(),role);
            return ResponseEntity.status(200).body(new JwtResponse(token));
        } catch (BadCredentialsException e){
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(){
        return ResponseEntity.status(403).body("User doesn't have the access");
    }
}

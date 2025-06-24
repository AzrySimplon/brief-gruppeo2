package fr.simplon.gruppeo.controller;

import fr.simplon.gruppeo.configuration.JwtUtils;
import fr.simplon.gruppeo.model.User;
import fr.simplon.gruppeo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private UserController userController;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, AuthenticationManager authenticationManager, UserController userController) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.userController = userController;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User userReq) {
        User user = userController.getUserByUsername(userReq.getUsername()).getBody();
    
    try {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userReq.getUsername(), userReq.getPassword())
        );
        
        if (authentication.isAuthenticated()) {
            ResponseCookie cookie = ResponseCookie.from("jwt", jwtUtils.generateToken(user))
                    .httpOnly(true)
                    .path("/")
                    .maxAge(24 * 60 * 60)
                    .sameSite("Strict")
                    .build();

            Map<String, Object> authData = new HashMap<>();
            authData.put("token", jwtUtils.generateToken(user));
            authData.put("type", "Bearer");
            authData.put("userId", user.getId());

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(authData);
        }
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Invalid username or password");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        
    } catch (AuthenticationException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Invalid username or password");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
}
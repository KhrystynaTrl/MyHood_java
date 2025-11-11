package it.start2impact.MyHood.controller;

import it.start2impact.MyHood.config.security.JwtUtilities;
import it.start2impact.MyHood.dto.LoginDto;
import it.start2impact.MyHood.exceptions.UnauthorizedException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtilities jwtUtilities;

    @Autowired
    public LoginController(UserDetailsService userDetailsService, BCryptPasswordEncoder encoder, JwtUtilities jwtUtilities) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
        this.jwtUtilities = jwtUtilities;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto loginDto) throws UnauthorizedException{
        logger.info("LoginController.login - {}", loginDto);
        UserDetails user = userDetailsService.loadUserByUsername(loginDto.getEmail());
        if(encoder.matches(loginDto.getPassword(), user.getPassword())){
            String token = jwtUtilities.generateToken(user);

            return ResponseEntity.status(HttpStatus.OK).body(token);
        }
        throw new UnauthorizedException("Wrong password");
    }
}

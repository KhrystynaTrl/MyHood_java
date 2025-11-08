package it.start2impact.MyHood.controller;

import it.start2impact.MyHood.dto.UserDto;
import it.start2impact.MyHood.exceptions.MyHoodException;
import it.start2impact.MyHood.exceptions.NotFoundException;
import it.start2impact.MyHood.service.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.manager.DummyProxySession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserDto user) throws MyHoodException {
        logger.info("UserController.registerUser - {}", user);
        UserDto response = userService.registerUser(user);
        return ResponseEntity.ok(response);
    }

}

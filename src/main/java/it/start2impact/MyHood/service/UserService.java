package it.start2impact.MyHood.service;

import it.start2impact.MyHood.dto.UserDto;
import it.start2impact.MyHood.entities.LocationEntity;
import it.start2impact.MyHood.entities.UserEntity;
import it.start2impact.MyHood.exceptions.NotFoundException;
import it.start2impact.MyHood.mappers.UserMapper;
import it.start2impact.MyHood.repositories.LocationRepository;
import it.start2impact.MyHood.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final LocationRepository locationRepository;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.locationRepository = locationRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDto registerUser(UserDto user) throws NotFoundException {
        logger.info("UserService.registerUser - {}", user);
        UserEntity entity = UserMapper.fromDTO(user);
        LocationEntity locationEntity = locationRepository.findByLocationIgnoreCase(user.getLocation()).orElseThrow(()->new NotFoundException("Location not found"));
        entity.setLocationEntity(locationEntity);
        entity.setPassword(encoder.encode(entity.getPassword()));
        entity = userRepository.save(entity);

        return UserMapper.fromEntity(entity);
    }


}

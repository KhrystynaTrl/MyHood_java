package it.start2impact.MyHood;

import it.start2impact.MyHood.dto.UserDto;
import it.start2impact.MyHood.entities.LocationEntity;
import it.start2impact.MyHood.entities.UserEntity;
import it.start2impact.MyHood.exceptions.MyHoodException;
import it.start2impact.MyHood.exceptions.NotFoundException;
import it.start2impact.MyHood.repositories.LocationRepository;
import it.start2impact.MyHood.repositories.UserRepository;
import it.start2impact.MyHood.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void loadUserByUsername_userExists_returnsUserEntity() {
        // ARRANGE
        String email = "mario@email.com";
        UserEntity mockUser = new UserEntity();
        mockUser.setEmail(email);
        mockUser.setName("Mario");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        // ACT
        UserEntity result = (UserEntity) userService.loadUserByUsername(email);

        // ASSERT
        assertEquals(email, result.getEmail());
        verify(userRepository).findByEmail(email);
    }

    @Test
    void loadUserByUsername_userNotFound_throwsUsernameNotFoundException() {
        String email = "nonexistent@email.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userService.loadUserByUsername(email)
        );
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void registerUser_validUser_returnsUserDto() throws Exception {
        // ARRANGE
        UserDto inputDto = new UserDto();
        inputDto.setName("Mario");
        inputDto.setSurname("Rossi");
        inputDto.setEmail("newuser@email.com");
        inputDto.setPassword("password123");
        inputDto.setLocation("Roma");

        LocationEntity location = new LocationEntity();
        location.setLocation("Roma");

        UserEntity savedEntity = new UserEntity();
        savedEntity.setEmail("newuser@email.com");
        savedEntity.setName("Mario");

        when(locationRepository.findByLocationIgnoreCase("Roma")).thenReturn(Optional.of(location));
        when(userRepository.existsByEmail("newuser@email.com")).thenReturn(false);
        when(encoder.encode("password123")).thenReturn("hashedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedEntity);

        // ACT
        UserDto result = userService.registerUser(inputDto);

        // ASSERT
        assertNotNull(result);
        verify(locationRepository).findByLocationIgnoreCase("Roma");
        verify(userRepository).existsByEmail("newuser@email.com");
        verify(encoder).encode("password123");
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void registerUser_locationNotFound_throwsNotFoundException() {
        UserDto userDto = new UserDto();
        userDto.setLocation("CittaInesistente");

        when(locationRepository.findByLocationIgnoreCase("CittaInesistente"))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> userService.registerUser(userDto)
        );
        assertEquals("Location not found", exception.getMessage());
    }

    @Test
    void registerUser_userAlreadyExists_throwsMyHoodException() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setLocation("Roma");
        userDto.setEmail("existing@email.com");

        LocationEntity location = new LocationEntity();
        when(locationRepository.findByLocationIgnoreCase("Roma")).thenReturn(Optional.of(location));
        when(userRepository.existsByEmail("existing@email.com")).thenReturn(true);

        MyHoodException exception = assertThrows(
                MyHoodException.class,
                () -> userService.registerUser(userDto)
        );
        assertEquals("User already exist", exception.getMessage());
    }
}

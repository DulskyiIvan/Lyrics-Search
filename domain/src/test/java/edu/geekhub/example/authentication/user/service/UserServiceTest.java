package edu.geekhub.example.authentication.user.service;

import edu.geekhub.example.TestApplication;
import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.authentication.user.model.UserDto;
import edu.geekhub.example.authentication.user.repository.UserRepository;
import edu.geekhub.example.service.role.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ContextConfiguration(classes = {UserService.class, TestApplication.class,
        UserDtoMapper.class})
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDtoMapper userDtoMapper;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        User user = new User();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setPassword("1234");
        user.setUsername("user");
        user.setRole(new Role().setId(1).setTitle("USER"));

        UserDto actualUser = userService.saveUser(user);
        UserDto userDto = userDtoMapper.apply(userRepository.findByUsername(user.getUsername()));

        userDto.setId(actualUser.getId());

        assertEquals(userDto, actualUser);
    }

    @Test
    void testGetUserByUsername() {
        User user = new User();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setPassword("1234");
        user.setUsername("user");
        user.setRole(new Role().setId(1).setTitle("USER"));

        UserDto userDto = userService.saveUser(user);
        User actualUser = userService.getUserByUsername(userDto.getUsername());
        user.setId(userDto.getId());

        assertEquals(user, actualUser);
    }

    @Test
    void testGetUserByEmail() {
        User user = new User();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setPassword("1234");
        user.setUsername("user");
        user.setEmail("example@com.ua");
        user.setRole(new Role().setId(1).setTitle("USER"));

        UserDto userDto = userService.saveUser(user);
        User actualUser = userService.getUserByEmail(userDto.getEmail());
        user.setId(userDto.getId());

        assertEquals(user, actualUser);
    }

    @Test
    void testGetUserById() {
        User user = new User();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setPassword("1234");
        user.setUsername("user");
        user.setRole(new Role().setId(1).setTitle("USER"));

        UserDto userDto = userService.saveUser(user);
        User actualUser = userService.getUserById(user.getId());
        user.setId(userDto.getId());

        assertEquals(user, actualUser);
    }

    @Test
    void testGetUsersDtoByUsername() {
        Pageable pageable = PageRequest.of(0, 1);
        User user = new User();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setPassword("1234");
        user.setUsername("user");
        user.setRole(new Role().setId(1).setTitle("USER"));

        UserDto userDto = userService.saveUser(user);

        Page<UserDto> expectedPage = new PageImpl<>(List.of(userDto));

        Page<UserDto> actualPage = userService.getUsersDtoByUsername("user", pageable);

        assertEquals(expectedPage.getNumberOfElements(), actualPage.getNumberOfElements());
        assertEquals(expectedPage.getNumber(), actualPage.getNumber());
        assertEquals(expectedPage.getTotalPages(), actualPage.getTotalPages());
        assertEquals(expectedPage.getSize(), actualPage.getSize());
        assertEquals(expectedPage.getContent(), actualPage.getContent());
    }

    @Test
    void testGetPageAllUsersDto() {
        Pageable pageable = PageRequest.of(0, 1);
        User user = new User();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setPassword("1234");
        user.setUsername("user");
        user.setRole(new Role().setId(1).setTitle("USER"));

        UserDto userDto = userService.saveUser(user);

        Page<UserDto> expectedPage = new PageImpl<>(List.of(userDto));

        Page<UserDto> actualPage = userService.getPageAllUsers(pageable);

        assertEquals(expectedPage.getNumberOfElements(), actualPage.getNumberOfElements());
        assertEquals(expectedPage.getNumber(), actualPage.getNumber());
        assertEquals(expectedPage.getSize(), actualPage.getSize());
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setFirstName("UserFirstName");
        user.setLastName("UserLastName");
        user.setPassword("1234");
        user.setUsername("user");
        user.setRole(new Role().setId(1).setTitle("USER"));

        userService.saveUser(user);

        user.setFirstName("FirstName");
        UserDto actualDto = userService.updateUser(user);

        assertEquals(userDtoMapper.apply(user), actualDto);

    }

}
package edu.geekhub.example.authentication.user.service;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.authentication.user.model.UserDto;
import edu.geekhub.example.authentication.user.repository.UserRepository;
import edu.geekhub.example.service.role.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDtoMapper userDtoMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
        UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userDtoMapper = userDtoMapper;
    }

    public UserDto saveUser(User user) {
        return userDtoMapper.apply(userRepository.save(user));
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
            .orElseThrow(
                () -> new NotFoundException("User with id " + id + " not found")
            );
    }

    public Page<UserDto> getUsersDtoByUsername(String username, Pageable pageable) {
        if (username == null || username.isEmpty()) {
            return Page.empty();
        } else {
            return userRepository.findAllByUsernameContainingIgnoreCase(pageable, username)
                .map(userDtoMapper);
        }
    }

    public Page<UserDto> getPageAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
            .map(userDtoMapper);
    }


    public UserDto setRole(UUID userId, String role) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(roleRepository.findByTitle(role)
            .orElseThrow(() -> new RuntimeException("Role not found")));
        return userDtoMapper.apply(userRepository.save(user));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDto updateUser(User user) {
        return userDtoMapper.apply(userRepository.save(user));
    }
}

package edu.geekhub.example.service.role.service;

import edu.geekhub.example.service.role.model.Role;
import edu.geekhub.example.service.role.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByTitle(String title) {
        return roleRepository.findByTitle(title).orElseThrow(
            () -> new NotFoundException("Role not found with title = " + title)
        );
    }
}

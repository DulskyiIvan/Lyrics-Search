package edu.geekhub.example.service.role.service;

import edu.geekhub.example.TestApplication;
import edu.geekhub.example.service.role.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ContextConfiguration(classes = {RoleService.class, TestApplication.class})
class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Test
    void getGenreByTitle() {
        String role = "USER";

        Role actualRole = roleService.getRoleByTitle(role);

        assertNotNull(actualRole);
        assertEquals(role, actualRole.getTitle());
    }
}
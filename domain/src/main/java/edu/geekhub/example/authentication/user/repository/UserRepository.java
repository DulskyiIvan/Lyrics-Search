package edu.geekhub.example.authentication.user.repository;

import edu.geekhub.example.authentication.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);

    Page<User> findAllByUsernameContainingIgnoreCase(Pageable pageable, String username);

    User findByEmail(String email);
}

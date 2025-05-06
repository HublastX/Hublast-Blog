package com.hublast.api_blog.Repository;

import com.hublast.api_blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods can be defined here if needed
    User findByUsername(String username);
    User findByEmail(String email);
}

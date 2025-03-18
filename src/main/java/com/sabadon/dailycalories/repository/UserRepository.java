package com.sabadon.dailycalories.repository;

import com.sabadon.dailycalories.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

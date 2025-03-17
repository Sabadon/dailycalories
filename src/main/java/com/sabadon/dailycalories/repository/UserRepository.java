package com.sabadon.dailycalories.repository;

import com.sabadon.dailycalories.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

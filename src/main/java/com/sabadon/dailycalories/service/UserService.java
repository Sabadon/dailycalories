package com.sabadon.dailycalories.service;

import com.sabadon.dailycalories.domain.User;
import com.sabadon.dailycalories.dto.UserDto;
import com.sabadon.dailycalories.dto.mapper.UserMapper;
import com.sabadon.dailycalories.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserMapper mapper;
    private UserRepository userRepository;

    public User createNewUser(UserDto userDto) {
        return userRepository.save(
                mapper.toEntity(userDto)
        );
    }

}

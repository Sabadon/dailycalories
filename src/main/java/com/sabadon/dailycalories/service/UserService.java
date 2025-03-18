package com.sabadon.dailycalories.service;

import com.sabadon.dailycalories.entities.User;
import com.sabadon.dailycalories.dto.user.UserDto;
import com.sabadon.dailycalories.dto.mapper.UserMapper;
import com.sabadon.dailycalories.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserMapper mapper;
    private UserRepository userRepository;

    @Transactional
    public UserDto createNewUser(UserDto userDto) {
        final User newUser = userRepository.save(
                mapper.toEntity(userDto)
        );

        return mapper.toDto(newUser);
    }

    public UserDto getUser(Long id) {
        final User user = tryFindUser(id);
        return mapper.toDto(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        final User user = tryFindUser(id);
        userRepository.delete(user);
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto newUserInfo) {
        User user = tryFindUser(id);
        user = mapper.partialUpdate(newUserInfo, user);
        userRepository.save(user);
        return mapper.toDto(user);
    }

    public User tryFindUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));
    }

}

package com.sabadon.dailycalories.controller;


import com.sabadon.dailycalories.dto.user.UserDto;
import com.sabadon.dailycalories.dto.marker.OnCreate;
import com.sabadon.dailycalories.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService service;

    @PostMapping
    public ResponseEntity<UserDto> createNewUser(@RequestBody @Validated(OnCreate.class) UserDto userDto,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        final UserDto newUser = service.createNewUser(userDto);
        final URI location = uriComponentsBuilder.path("/api/user/{id}")
                .buildAndExpand(newUser.id()).toUri();
        return ResponseEntity
                .created(location)
                .body(newUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(
                service.getUser(userId)
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        service.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody @Validated UserDto userDto) {
        return ResponseEntity.ok(
                service.updateUser(userId, userDto)
        );
    }

}

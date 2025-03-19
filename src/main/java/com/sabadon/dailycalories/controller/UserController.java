package com.sabadon.dailycalories.controller;


import com.sabadon.dailycalories.dto.marker.OnCreate;
import com.sabadon.dailycalories.dto.marker.OnUpdate;
import com.sabadon.dailycalories.dto.user.UserDto;
import com.sabadon.dailycalories.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
@Tag(name = "Пользователи", description = "Взаимодействие с пользователями")
public class UserController {

    private UserService service;

    @PostMapping
    @Operation(
            summary = "Добавление нового пользователя",
            description = "Позволяет добавить нового пользователя"
    )
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
    @Operation(
            summary = "Получение пользователя",
            description = "Отображает информацию о указаном пользователе"
    )
    public ResponseEntity<UserDto> getUser(@PathVariable
                                           @Parameter(description = "Идентификатор пользователя", required = true)
                                           Long userId) {
        return ResponseEntity.ok(
                service.getUser(userId)
        );
    }

    @DeleteMapping("/{userId}")
    @Operation(
            summary = "Удаление пользователя",
            description = "Позволяет удалить указаного пользователя"
    )
    public ResponseEntity<Void> deleteUser(@PathVariable
                                           @Parameter(description = "Идентификатор пользователя", required = true)
                                           Long userId) {
        service.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{userId}")
    @Operation(
            summary = "Изменение пользователя",
            description = "Позволяет изменить информацию о пользователе"
    )
    public ResponseEntity<UserDto> updateUser(@PathVariable
                                              @Parameter(description = "Идентификатор пользователя", required = true)
                                              Long userId, @RequestBody @Validated(OnUpdate.class) UserDto userDto) {
        return ResponseEntity.ok(
                service.updateUser(userId, userDto)
        );
    }

}

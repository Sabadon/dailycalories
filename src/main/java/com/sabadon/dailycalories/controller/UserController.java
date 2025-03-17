package com.sabadon.dailycalories.controller;


import com.sabadon.dailycalories.domain.User;
import com.sabadon.dailycalories.dto.UserDto;
import com.sabadon.dailycalories.dto.mapper.UserMapper;
import com.sabadon.dailycalories.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    private UserMapper mapper;
    private UserService service;

    @PostMapping
    public ResponseEntity<UserDto> createNewUser(@RequestBody @Validated UserDto userDto,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        final User newUser = service.createNewUser(userDto);
        final URI location = uriComponentsBuilder.path("/user/{id}")
                .buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity
                .created(location)
                .body(mapper.toDto(newUser));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}

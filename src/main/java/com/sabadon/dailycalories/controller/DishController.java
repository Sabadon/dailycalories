package com.sabadon.dailycalories.controller;

import com.sabadon.dailycalories.dto.DishDto;
import com.sabadon.dailycalories.dto.marker.OnCreate;
import com.sabadon.dailycalories.service.DishService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@AllArgsConstructor
@RestController
@RequestMapping("/api/dish")
public class DishController {

    private DishService service;

    @PostMapping
    public ResponseEntity<DishDto> createNewDish(@RequestBody @Validated(OnCreate.class) DishDto dishDto,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        final DishDto newDish = service.createNewDish(dishDto);
        final URI location = uriComponentsBuilder.path("/api/dish/{id}")
                .buildAndExpand(newDish.id()).toUri();
        return ResponseEntity
                .created(location)
                .body(newDish);
    }

    @GetMapping("/{dishId}")
    public ResponseEntity<DishDto> getDish(@PathVariable Long dishId) {
        return ResponseEntity.ok(
                service.getDish(dishId)
        );
    }

    @DeleteMapping("/{dishId}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long dishId) {
        service.deleteDish(dishId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{dishId}")
    public ResponseEntity<DishDto> updateDish(@PathVariable Long dishId,
                                              @RequestBody @Validated DishDto dishDto) {
        return ResponseEntity.ok(
                service.updateDish(dishId, dishDto)
        );
    }

}

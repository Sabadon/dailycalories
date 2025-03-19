package com.sabadon.dailycalories.controller;

import com.sabadon.dailycalories.dto.dish.DishDto;
import com.sabadon.dailycalories.dto.marker.OnCreate;
import com.sabadon.dailycalories.dto.marker.OnUpdate;
import com.sabadon.dailycalories.service.DishService;
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
@RequestMapping("/api/dish")
@Tag(name = "Блюда", description = "Взаимодействие с блюдами")
public class DishController {

    private DishService service;

    @PostMapping
    @Operation(
            summary = "Добавление нового блюда",
            description = "Позволяет создать новое блюдо"
    )
    public ResponseEntity<DishDto> createNewDish(@RequestBody
                                                 @Validated(OnCreate.class)
                                                 DishDto dishDto,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        final DishDto newDish = service.createNewDish(dishDto);
        final URI location = uriComponentsBuilder.path("/api/dish/{id}")
                .buildAndExpand(newDish.id()).toUri();
        return ResponseEntity
                .created(location)
                .body(newDish);
    }

    @GetMapping("/{dishId}")
    @Operation(
            summary = "Получение блюда",
            description = "Позволяет получить информацию о уже существующем блюде"
    )
    public ResponseEntity<DishDto> getDish(@PathVariable
                                           @Parameter(description = "Идентификатор блюда", required = true)
                                           Long dishId) {
        return ResponseEntity.ok(
                service.getDish(dishId)
        );
    }

    @DeleteMapping("/{dishId}")
    @Operation(
            summary = "Удаление блюда",
            description = "Позволяет удалить блюдо"
    )
    public ResponseEntity<Void> deleteDish(@PathVariable
                                           @Parameter(description = "Идентификатор блюда", required = true)
                                           Long dishId) {
        service.deleteDish(dishId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{dishId}")
    @Operation(
            summary = "Изменение блюда",
            description = "Позволяет изменять информацию о блюде"
    )
    public ResponseEntity<DishDto> updateDish(@PathVariable
                                              @Parameter(description = "Идентификатор блюда", required = true)
                                              Long dishId,
                                              @RequestBody
                                              @Validated(OnUpdate.class)
                                              DishDto dishDto) {
        return ResponseEntity.ok(
                service.updateDish(dishId, dishDto)
        );
    }

}

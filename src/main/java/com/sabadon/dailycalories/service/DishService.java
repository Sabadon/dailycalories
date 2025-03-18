package com.sabadon.dailycalories.service;

import com.sabadon.dailycalories.dto.dish.DishDto;
import com.sabadon.dailycalories.dto.mapper.DishMapper;
import com.sabadon.dailycalories.entities.Dish;
import com.sabadon.dailycalories.repository.DishRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DishService {

    private DishMapper mapper;
    private DishRepository dishRepository;

    @Transactional
    public DishDto createNewDish(DishDto dishDto) {
        final Dish newDish = dishRepository.save(
                mapper.toEntity(dishDto)
        );

        return mapper.toDto(newDish);
    }

    public DishDto getDish(Long id) {
        final Dish dish = tryFindDish(id);
        return mapper.toDto(dish);
    }

    @Transactional
    public void deleteDish(Long id) {
        final Dish dish = tryFindDish(id);
        dishRepository.delete(dish);
    }

    @Transactional
    public DishDto updateDish(Long id, DishDto newDishInfo) {
        Dish dish = tryFindDish(id);
        dish = mapper.partialUpdate(newDishInfo, dish);
        dishRepository.save(dish);
        return mapper.toDto(dish);
    }

    private Dish tryFindDish(Long id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dish with id " + id + " not found"));
    }

}

package com.sabadon.dailycalories.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "meal_dishes")
public class MealDish {

    @EmbeddedId
    private MealDishPk pk;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("mealId")
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("dishId")
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    @Column(name = "portion_size", nullable = false)
    private Long portionSize;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class MealDishPk implements Serializable {

        @Serial
        private static final long serialVersionUID = 8339932422097961852L;

        private Long mealId;
        private Long dishId;
    }
}

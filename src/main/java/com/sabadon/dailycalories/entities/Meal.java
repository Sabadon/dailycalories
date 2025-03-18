package com.sabadon.dailycalories.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "meal_id_seq"
    )
    @SequenceGenerator(
            name = "meal_id_seq",
            sequenceName = "meal_id_seq",
            allocationSize = 1
    )
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "meal_time")
    private LocalDateTime mealTime = LocalDateTime.now();
    @Column(name = "total_calories")
    private BigDecimal totalCalories;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealDish> mealDishes = new ArrayList<>();

    //TODO: сделать что бы при добавлении одного и того же блюда размер порции просто сумимировался
    public void addDish(Dish dish, Long portionSize) {
        final MealDish md = new MealDish(
                new MealDish.MealDishPk(getId(), dish.getId()),
                this,
                dish,
                portionSize
        );

        mealDishes.add(md);
    }

    public void removeDish(Dish dish) {
        mealDishes.removeIf(mealDish -> mealDish.getDish().equals(dish));
    }
}

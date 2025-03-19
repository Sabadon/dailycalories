package com.sabadon.dailycalories.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "meals")
@NamedEntityGraph(
        name = "Meal.withDishesAndDetails",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode(value = "mealDishes", subgraph = "mealDishesSubgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "mealDishesSubgraph",
                        attributeNodes = {
                                @NamedAttributeNode("dish")
                        }
                )
        }
)
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

    public void addDish(Dish dish, Long portionSize) {
        final Optional<MealDish> existedDish = mealDishes.stream()
                .filter(md -> md.getDish().getId().equals(dish.getId()))
                .findFirst();
        if (existedDish.isPresent()) {
            final MealDish md = existedDish.get();
            md.setPortionSize(md.getPortionSize() + portionSize);
        } else {
            final MealDish md = new MealDish(
                    new MealDish.MealDishPk(getId(), dish.getId()),
                    this,
                    dish,
                    portionSize
            );
            mealDishes.add(md);
        }
    }

    public void removeDish(Dish dish) {
        mealDishes.removeIf(mealDish -> mealDish.getDish().equals(dish));
    }
}

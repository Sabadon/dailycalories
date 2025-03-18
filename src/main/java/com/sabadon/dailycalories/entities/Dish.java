package com.sabadon.dailycalories.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dishes")
public class Dish {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "dishes_id_seq"
    )
    @SequenceGenerator(
            name = "dishes_id_seq",
            sequenceName = "dishes_id_seq",
            allocationSize = 1
    )
    private Long id;
    private String name;
    @Column(name = "calories_per_portion")
    private BigDecimal caloriesPerPortion;
    private BigDecimal proteins;
    private BigDecimal fats;
    private BigDecimal carbs;


}

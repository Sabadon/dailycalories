package com.sabadon.dailycalories.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "meals_id_seq"
    )
    @SequenceGenerator(
            name = "meals_id_seq",
            sequenceName = "meals_id_seq",
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

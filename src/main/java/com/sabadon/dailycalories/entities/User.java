package com.sabadon.dailycalories.entities;

import com.sabadon.dailycalories.enums.Gender;
import com.sabadon.dailycalories.enums.Goal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_id_seq"
    )
    @SequenceGenerator(
            name = "users_id_seq",
            sequenceName = "users_id_seq",
            allocationSize = 1
    )
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private Integer age;
    private BigDecimal weight;
    private BigDecimal height;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Goal goal;
    @Column(name = "daily_calories")
    private BigDecimal dailyCalories;

    @PrePersist
    @PreUpdate
    private void calculateDailyCalories() {
        final BigDecimal activityFactor = new BigDecimal("1.2");
        final BigDecimal bmr;
        if (getGender() == Gender.MALE) {
            bmr = new BigDecimal("88.362")
                    .add(new BigDecimal("13.397").multiply(getWeight()))
                    .add(new BigDecimal("4.799").multiply(getHeight()))
                    .subtract(new BigDecimal("5.677").multiply(BigDecimal.valueOf(getAge())));
        } else {
            bmr = new BigDecimal("447.593")
                    .add(new BigDecimal("9.247").multiply(getWeight()))
                    .add(new BigDecimal("3.098").multiply(getHeight()))
                    .subtract(new BigDecimal("4.330").multiply(BigDecimal.valueOf(getAge())));
        }

        BigDecimal tmpDailyCal = bmr.multiply(activityFactor);
        switch (getGoal()) {
            case LOSE:
                tmpDailyCal = tmpDailyCal.multiply(new BigDecimal("0.9"));
                break;
            case GAIN:
                tmpDailyCal = tmpDailyCal.multiply(new BigDecimal("1.1"));
                break;
            case MAINTAIN:
                break;
        }

         setDailyCalories(tmpDailyCal.setScale(2, RoundingMode.HALF_UP));
    }

}

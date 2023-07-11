package com.zoo.zoo.animal;

import com.zoo.zoo.enclosure.Enclosure;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "animal")
@AllArgsConstructor
@NoArgsConstructor
public class Animal {

    @Id
    @GeneratedValue
    private Long id;
    private String species;
    @Enumerated(EnumType.STRING)
    private FoodType food;
    private Integer amount;
    @ManyToOne
    private Enclosure enclosure;

}

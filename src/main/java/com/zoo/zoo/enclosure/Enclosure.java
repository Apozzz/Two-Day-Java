package com.zoo.zoo.enclosure;

import java.util.ArrayList;
import java.util.List;

import com.zoo.zoo.animal.Animal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "enclosure")
@AllArgsConstructor
@NoArgsConstructor
public class Enclosure {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Size size;
    @Enumerated(EnumType.STRING)
    private Location location;
    @ElementCollection
    private List<String> objects;
    @OneToMany(mappedBy = "enclosure", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Animal> animals = new ArrayList<>();

    public void addAnimal(Animal animal) {
        animal.setEnclosure(this);
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
        animal.setEnclosure(null);
    }

    public void removeAnimalBySpecies(String species) {
        animals.removeIf(animal -> animal.getSpecies().equals(species));
    }

}

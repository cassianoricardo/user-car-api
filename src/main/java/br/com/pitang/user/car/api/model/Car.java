package br.com.pitang.user.car.api.model;


import br.com.pitang.user.car.api.model.dto.CarDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int year;

    private String licensePlate;

    private String model;

    private String color;


    public CarDTO parseToDTO(){
        return CarDTO.builder().id(this.id).model(this.model).color(this.color).licensePlate(this.licensePlate).build();
    }
}
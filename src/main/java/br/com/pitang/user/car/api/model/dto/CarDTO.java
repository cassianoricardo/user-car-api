package br.com.pitang.user.car.api.model.dto;

import br.com.pitang.user.car.api.model.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

        private Long id;

        private int year;

        private String licensePlate;

        private String model;

        private String color;

        public Car parseToEntity(){
                return Car.builder().id(this.id).model(this.model).color(this.color).licensePlate(this.licensePlate).build();
        }
}
package br.com.pitang.user.car.api.model.dto;

import br.com.pitang.user.car.api.model.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {


        private int year;

        private String licensePlate;

        private String model;

        private String color;

        private Long userId;

        public Car parseToEntity(){
                return Car.builder()
                          .year(this.year)
                          .model(this.model)
                          .color(this.color)
                          .licensePlate(this.licensePlate).build();
        }
}
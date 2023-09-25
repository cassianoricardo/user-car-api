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


        private Long id;

        private int year;

        private String licensePlate;

        private String model;

        private String color;

        private byte[] photo;

        public Car parseToEntity(){
                return Car.builder()
                          .photo(this.photo)
                          .year(this.year)
                          .model(this.model)
                          .color(this.color)
                          .licensePlate(this.licensePlate).build();
        }
}
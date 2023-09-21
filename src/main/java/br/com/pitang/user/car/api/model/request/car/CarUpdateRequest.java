package br.com.pitang.user.car.api.model.request.car;


import br.com.pitang.user.car.api.model.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarUpdateRequest {

    private int year;
    private String licensePlate;
    private String model;
    private String color;
    private Long userId;

    public Car parseToEntity(){
        return Car.builder().year(year)
                               .color(color)
                               .model(model)
                               .licensePlate(licensePlate)
                               //.userId(userId)
                               .build();
    }
}
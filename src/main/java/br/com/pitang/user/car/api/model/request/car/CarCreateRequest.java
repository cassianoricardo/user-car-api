package br.com.pitang.user.car.api.model.request.car;


import br.com.pitang.user.car.api.model.entity.Car;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarCreateRequest {

    @NotNull(message = "Missing year")
    private Integer year;
    @NotBlank(message = "Missing licensePlate")
    private String licensePlate;
    @NotBlank(message = "Missing model")
    private String model;
    @NotBlank(message = "Missing color")
    private String color;

    public Car parseToEntity(){
        return Car.builder().model(model).year(year).color(color).licensePlate(licensePlate).build();
    }
}

package br.com.pitang.user.car.api.model.entity;


import br.com.pitang.user.car.api.model.dto.CarDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int year;

    @Column(unique = true)
    private String licensePlate;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String color;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;


    public CarDTO parseToDTO() {
        return CarDTO.builder()
                     .id(id)
                     .year(year)
                     .model(model)
                     .color(color)
                     .licensePlate(licensePlate)
                     .build();
    }
}
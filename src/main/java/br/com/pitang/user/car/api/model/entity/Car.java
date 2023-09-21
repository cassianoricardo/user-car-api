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

    private int year;

    @Column(unique = true)
    private String licensePlate;

    private String model;

    private String color;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_user_id"),name = "user_id", referencedColumnName = "id")
    private User user;


    public CarDTO parseToDTO() {
        return CarDTO.builder()
                .year(this.year)
                .model(this.model)
                .color(this.color)
                .licensePlate(this.licensePlate)
                .userId(user.getId()).build();
    }
}
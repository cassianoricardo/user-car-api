package br.com.pitang.user.car.api.controller;

import br.com.pitang.user.car.api.model.dto.CarDTO;
import br.com.pitang.user.car.api.model.request.car.CarCreateRequest;
import br.com.pitang.user.car.api.model.request.car.CarUpdateRequest;
import br.com.pitang.user.car.api.service.car.CarCreateService;
import br.com.pitang.user.car.api.service.car.CarDeleteService;
import br.com.pitang.user.car.api.service.car.CarFindService;
import br.com.pitang.user.car.api.service.car.CarUpdateService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


@Validated
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("cars")
public class CarController {

    @Autowired
    private CarCreateService carCreateService;

    @Autowired
    private CarFindService carFindService;

    @Autowired
    private CarDeleteService carDeleteService;

    @Autowired
    private CarUpdateService carUpdateService;

    @GetMapping
    public List<CarDTO> getAllCars(){
        return carFindService.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void createCar(@Valid @RequestBody CarCreateRequest carCreateRequest){
        carCreateService.create(carCreateRequest);
    }

    @GetMapping("/{id}")
    public CarDTO getCar(@PathVariable Long id){
        return carFindService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCar(@PathVariable Long id){
        carDeleteService.delete(id);
    }

    @PutMapping("/{id}")
    public CarDTO updateCar(@PathVariable Long id, @RequestBody CarUpdateRequest carUpdateRequest){
        return carUpdateService.update(id, carUpdateRequest);
    }
}
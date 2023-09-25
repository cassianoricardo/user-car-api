package br.com.pitang.user.car.api.controller;

import br.com.pitang.user.car.api.model.dto.CarDTO;
import br.com.pitang.user.car.api.model.request.car.CarCreateRequest;
import br.com.pitang.user.car.api.model.request.car.CarUpdateRequest;
import br.com.pitang.user.car.api.service.car.CarCreateService;
import br.com.pitang.user.car.api.service.car.CarDeleteService;
import br.com.pitang.user.car.api.service.car.CarFindService;
import br.com.pitang.user.car.api.service.car.CarUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;


@Validated
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping(path = "cars"/*,  consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE*/)
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
    @Operation(summary = "Returns all cars of the logged in user")
    public List<CarDTO> getAllCars(){
        return carFindService.findAll();
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a new car to the logged in user")
    public void createCar(@Valid @RequestBody CarCreateRequest carCreateRequest){
        carCreateService.create(carCreateRequest);
    }

    @GetMapping(path="/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns information about a logged-in user's car")
    public CarDTO getCar(@PathVariable Long id){
        return carFindService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete a car from the logged in user")
    public void deleteCar(@PathVariable Long id){
        carDeleteService.delete(id);
    }

    @PutMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Returns logged in user information")
    public CarDTO updateCar(@PathVariable Long id, @RequestBody CarUpdateRequest carUpdateRequest){
        return carUpdateService.update(id, carUpdateRequest);
    }

    @PutMapping(path= "/{id}/photo", consumes = {MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Returns logged in user information")
    public void uploadPhoto(@PathVariable Long id, @RequestParam(name = "photo") MultipartFile photo) throws IOException {
        carUpdateService.updatePhoto(photo, id);
    }
}
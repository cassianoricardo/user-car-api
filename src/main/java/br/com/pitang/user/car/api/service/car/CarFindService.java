package br.com.pitang.user.car.api.service.car;

import br.com.pitang.user.car.api.exception.BadRequestException;
import br.com.pitang.user.car.api.model.dto.CarDTO;
import br.com.pitang.user.car.api.model.entity.Car;
import br.com.pitang.user.car.api.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarFindService {

    @Autowired
    private CarRepository carRepository;

    public List<CarDTO> findAll(){
        var cars = carRepository.findAll();
        return cars.stream().map(Car::parseToDTO).collect(Collectors.toList());
    }

    public CarDTO findById(Long id){
        var car = carRepository.findById(id).orElseThrow(()-> new BadRequestException("Car id: "+ id +" not found"));
        return car.parseToDTO();
    }
}
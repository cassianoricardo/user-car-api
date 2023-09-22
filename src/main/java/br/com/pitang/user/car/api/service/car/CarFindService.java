package br.com.pitang.user.car.api.service.car;

import br.com.pitang.user.car.api.exception.NotFoundException;
import br.com.pitang.user.car.api.model.dto.CarDTO;
import br.com.pitang.user.car.api.model.entity.Car;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.service.user.UserLoggedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CarFindService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserLoggedService userLoggedService;

    public List<CarDTO> findAll(){
        var user = userLoggedService.getUserAuthenticated();

        var cars = carRepository.findByUserId(user.getId());
        return cars.stream().map(Car::parseToDTO).collect(Collectors.toList());
    }

    public CarDTO findById(Long id){

        var user = userLoggedService.getUserAuthenticated();
        var car = carRepository.findByIdAndUserId(id, user.getId()).orElseThrow(()-> new NotFoundException("Car id: "+ id +" not found"));
        return car.parseToDTO();
    }
}
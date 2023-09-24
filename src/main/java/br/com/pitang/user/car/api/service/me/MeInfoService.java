package br.com.pitang.user.car.api.service.me;

import br.com.pitang.user.car.api.exception.NotFoundException;
import br.com.pitang.user.car.api.model.entity.Car;
import br.com.pitang.user.car.api.model.response.MeResponse;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.repository.UserRepository;
import br.com.pitang.user.car.api.service.user.UserLoggedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MeInfoService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserLoggedService userLoggedService;

    public MeResponse getInfo(){
        var userLogger = userLoggedService.getUserAuthenticated();
        var user = userRepository.findByLogin(userLogger.getUsername()).orElseThrow(()-> new NotFoundException("user: "+ userLogger.getUsername() +" not found"));
        var cars = carRepository.findByUserId(user.getId());
        var userDTO = user.parseToDTO();
        userDTO.setCars(cars.stream().map(Car::parseToDTO).collect(Collectors.toList()));
        return MeResponse.builder().user(userDTO).createdAt(user.getCreatedAt()).lastLogin(user.getLastLogin()).build();
    }
}

package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.exception.NotFoundException;
import br.com.pitang.user.car.api.model.entity.Car;
import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.model.dto.UserDTO;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserFindService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    public UserDTO findById(Long id){
        var userDTO = userRepository.findById(id).map(User::parseToDTO).orElseThrow(() -> new NotFoundException("User Not Found"));
        var cars = carRepository.findByUserId(userDTO.getId());
        var carsDTO = cars.stream().map(Car::parseToDTO).collect(Collectors.toList());
        userDTO.setCars(carsDTO);
        return userDTO;
    }

    public List<UserDTO> findAll(){

        var users = userRepository.findAll();
        var usersDTO = users.stream().map(User::parseToDTO).collect(Collectors.toList());

        usersDTO.forEach(userDTO -> {
            var cars = carRepository.findByUserId(userDTO.getId());
            var carsDTO = cars.stream().map(Car::parseToDTO).collect(Collectors.toList());
            userDTO.setCars(carsDTO);
        });

        return usersDTO;
    }

}
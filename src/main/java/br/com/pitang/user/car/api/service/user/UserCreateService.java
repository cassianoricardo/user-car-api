package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.exception.BadRequestException;
import br.com.pitang.user.car.api.model.dto.CarDTO;
import br.com.pitang.user.car.api.model.request.user.UserCreateRequest;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Calendar;
import java.util.stream.Collectors;

@Service
public class UserCreateService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public void create(UserCreateRequest userCreateRequest){

        userRepository.findByEmail(userCreateRequest.getEmail()).ifPresent(user -> {
            throw new BadRequestException("Email already exists");
        });

        userRepository.findByLogin(userCreateRequest.getLogin()).ifPresent(user -> {
            throw new BadRequestException("Login already exists");
        });

        userCreateRequest.getCars().stream().map(CarDTO::getLicensePlate)
                                            .forEach(licensePlate -> carRepository.findByLicensePlate(licensePlate)
                                                                                   .ifPresent(car -> {
                                                                                       throw new BadRequestException("License Plate already exists");
                                                                                   }));

        var user = userCreateRequest.parseToEntity();
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));

        user.setCreatedAt(new Date(Calendar.getInstance().getTime().getTime()));
        user.setLastLogin(new Date(Calendar.getInstance().getTime().getTime()));

        userRepository.save(user);

        var cars = userCreateRequest.getCars().stream().map(CarDTO::parseToEntity).collect(Collectors.toList());
        cars.forEach(car -> car.setUser(user));

        carRepository.saveAll(cars);
    }
}
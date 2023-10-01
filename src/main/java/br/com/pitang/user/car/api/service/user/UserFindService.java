package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.exception.NotFoundException;
import br.com.pitang.user.car.api.model.dto.UserDTO;
import br.com.pitang.user.car.api.model.entity.Car;
import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserFindService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    public UserDTO findById(Long id) {

        var userDTO = userRepository.findById(id).map(User::parseToDTO).orElseThrow(() -> new NotFoundException("User Not Found"));
        var cars = carRepository.findByUserIdOrderByCountUsedDescModelAsc(userDTO.getId());
        var carsDTO = cars.stream().map(Car::parseToDTO).collect(Collectors.toList());
        userDTO.setCars(carsDTO);
        return userDTO;
    }

    public List<UserDTO> findAll() {

        var users = userRepository.findAll();
        var cars = carRepository.findAll();

        Map<Integer, List<User>> map = new HashMap<>();

        users.forEach(user -> {

            var total = cars.stream().filter(car -> car.getUser().getId().equals(user.getId()))
                                            .map(Car::getCountUsed)
                                            .reduce(0, Integer::sum);

            if (map.get(total) == null) {
                map.put(total, new ArrayList<>(Collections.singletonList(user)));
            } else {
                map.get(total).add(user);
            }
        });

        map.values().forEach(listUsers -> listUsers.sort(Comparator.comparing(User::getFirstname)
                                                                   .thenComparing(User::getLastname)));
        var orderUsers = map.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                                                             .map(Map.Entry::getValue)
                                                             .flatMap(Collection::stream)
                                                             .map(User::parseToDTO)
                                                             .collect(Collectors.toList());
        orderUsers.forEach(userDTO -> {
            var carsDTO = cars.stream().filter(car -> car.getUser().getId().equals(userDTO.getId()))
                                                   .sorted(Comparator.comparing(Car::getCountUsed).reversed().thenComparing(Car::getModel))
                                                   .map(Car::parseToDTO)
                                                   .collect(Collectors.toList());
            userDTO.setCars(carsDTO);
        });

        return orderUsers;
    }

}
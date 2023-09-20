package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.model.dto.request.UserCreateRequest;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserCreateService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void create(UserCreateRequest userCreateRequest){
        var user = userCreateRequest.parseToEntity();
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        carRepository.saveAll(user.getCars());
        userRepository.save(user);
    }
}
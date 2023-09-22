package br.com.pitang.user.car.api.service.car;

import br.com.pitang.user.car.api.exception.BadRequestException;
import br.com.pitang.user.car.api.model.request.car.CarCreateRequest;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.service.user.UserLoggedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarCreateService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserLoggedService userLoggedService;

    @Transactional
    public void create(CarCreateRequest carCreateRequest){

        var user = userLoggedService.getUserAuthenticated();

        carRepository.findByLicensePlate(carCreateRequest.getLicensePlate()).ifPresent(car -> {
            throw new BadRequestException("Email already exists");
        });

        var car = carCreateRequest.parseToEntity();
        car.setUser(user);

        carRepository.save(car);
    }
}
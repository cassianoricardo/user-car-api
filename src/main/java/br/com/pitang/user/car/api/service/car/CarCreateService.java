package br.com.pitang.user.car.api.service.car;

import br.com.pitang.user.car.api.exception.BadRequestException;
import br.com.pitang.user.car.api.model.request.car.CarCreateRequest;
import br.com.pitang.user.car.api.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarCreateService {

    @Autowired
    private CarRepository carRepository;


    public void create(CarCreateRequest carCreateRequest){

        carRepository.findByLicensePlate(carCreateRequest.getLicensePlate()).ifPresent(car -> {
            throw new BadRequestException("Email already exists");
        });

        carRepository.save(carCreateRequest.parseToEntity());
    }
}
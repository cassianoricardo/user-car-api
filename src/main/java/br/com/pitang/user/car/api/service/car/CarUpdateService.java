package br.com.pitang.user.car.api.service.car;

import br.com.pitang.user.car.api.exception.BadRequestException;
import br.com.pitang.user.car.api.exception.NotFoundException;
import br.com.pitang.user.car.api.model.dto.CarDTO;
import br.com.pitang.user.car.api.model.request.car.CarUpdateRequest;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CarUpdateService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    public CarDTO update(Long id, CarUpdateRequest carUpdateRequest){

        var car = carRepository.findById(id).orElseThrow(()-> new NotFoundException("car id: "+id+" not found"));

        if (carUpdateRequest.getYear() != 0 && carUpdateRequest.getYear()!=car.getYear()){
            car.setYear(carUpdateRequest.getYear());
        }
        if (Objects.nonNull(carUpdateRequest.getModel()) && !carUpdateRequest.getModel().equals(car.getModel())){
            car.setModel(carUpdateRequest.getModel());
        }
        if (Objects.nonNull(carUpdateRequest.getColor()) && !carUpdateRequest.getColor().equals(car.getColor())){
            car.setColor(carUpdateRequest.getColor());
        }
        if (Objects.nonNull(carUpdateRequest.getUserId()) && !carUpdateRequest.getUserId().equals(car.getUser().getId())){

            var user = userRepository.findById(carUpdateRequest.getUserId()).orElseThrow(()-> new NotFoundException("user id: "+carUpdateRequest.getUserId()+" not found"));
            car.setUser(user);
        }
        if (Objects.nonNull(carUpdateRequest.getLicensePlate()) && !carUpdateRequest.getLicensePlate().equals(car.getLicensePlate())){

            carRepository.findByLicensePlate(carUpdateRequest.getLicensePlate()).ifPresent(c -> {
                throw new BadRequestException("License Plate already exists");
            });

            car.setLicensePlate(carUpdateRequest.getLicensePlate());
        }
        return null/* carRepository.save(carUpdateRequest.parseToEntity()).parseToDTO()*/;
    }
}

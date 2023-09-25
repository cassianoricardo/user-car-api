package br.com.pitang.user.car.api.service.car;

import br.com.pitang.user.car.api.exception.BadRequestException;
import br.com.pitang.user.car.api.exception.NotFoundException;
import br.com.pitang.user.car.api.model.dto.CarDTO;
import br.com.pitang.user.car.api.model.request.car.CarUpdateRequest;
import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.service.user.UserLoggedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Service
public class CarUpdateService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserLoggedService userLoggedService;

    @Transactional
    public CarDTO update(Long id, CarUpdateRequest carUpdateRequest){

        var user = userLoggedService.getUserAuthenticated();

        var car = carRepository.findByIdAndUserId(id, user.getId()).orElseThrow(()-> new NotFoundException("car id: "+id+" not found"));

        if (Objects.nonNull(carUpdateRequest.getYear())&& carUpdateRequest.getYear()!=car.getYear()){
            car.setYear(carUpdateRequest.getYear());
        }
        if (Objects.nonNull(carUpdateRequest.getModel()) && !carUpdateRequest.getModel().equals(car.getModel())){
            car.setModel(carUpdateRequest.getModel());
        }
        if (Objects.nonNull(carUpdateRequest.getColor()) && !carUpdateRequest.getColor().equals(car.getColor())){
            car.setColor(carUpdateRequest.getColor());
        }
        if (Objects.nonNull(carUpdateRequest.getLicensePlate()) && !carUpdateRequest.getLicensePlate().equals(car.getLicensePlate())){

            carRepository.findByLicensePlateAndUserId(carUpdateRequest.getLicensePlate(), user.getId()).ifPresent(c -> {
                throw new BadRequestException("License Plate already exists");
            });

            car.setLicensePlate(carUpdateRequest.getLicensePlate());
        }
        return carRepository.save(car).parseToDTO();
    }


    @Transactional
    public CarDTO updatePhoto(MultipartFile photo, Long carId) throws IOException {

        var user = userLoggedService.getUserAuthenticated();
        var car = carRepository.findByIdAndUserId(carId, user.getId()).orElseThrow(() -> new NotFoundException("Car not found"));
        car.setPhoto(photo.getBytes());
        return carRepository.save(car).parseToDTO();
    }
}
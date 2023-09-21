package br.com.pitang.user.car.api.service.car;

import br.com.pitang.user.car.api.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarDeleteService {

    @Autowired
    private CarRepository carRepository;

    public void delete(Long id){
        carRepository.deleteById(id);
    }
}
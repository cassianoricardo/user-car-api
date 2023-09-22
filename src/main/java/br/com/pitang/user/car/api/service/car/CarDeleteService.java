package br.com.pitang.user.car.api.service.car;

import br.com.pitang.user.car.api.repository.CarRepository;
import br.com.pitang.user.car.api.service.user.UserLoggedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarDeleteService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserLoggedService userLoggedService;

    @Transactional
    public void delete(Long id){
        var user = userLoggedService.getUserAuthenticated();
        carRepository.deleteByIdAndUserId(id, user.getId());
    }
}
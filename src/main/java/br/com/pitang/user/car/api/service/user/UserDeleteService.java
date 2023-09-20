package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDeleteService {

    @Autowired
    UserRepository userRepository;

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}
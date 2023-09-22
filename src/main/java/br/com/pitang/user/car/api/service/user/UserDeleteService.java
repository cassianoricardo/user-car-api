package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDeleteService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}
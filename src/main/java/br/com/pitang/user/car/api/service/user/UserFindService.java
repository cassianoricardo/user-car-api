package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.exception.NotFoundException;
import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.model.dto.UserDTO;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserFindService {

    @Autowired
    UserRepository userRepository;

    public UserDTO findById(Long id){
        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not Found"));
        return user.parseToDTO();
    }

    public List<UserDTO> findAll(){
        var users = userRepository.findAll();
        return users.stream().map(User::parseToDTO).collect(Collectors.toList());
    }

}
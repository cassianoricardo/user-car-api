package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.model.dto.UserDTO;
import br.com.pitang.user.car.api.model.dto.request.UserUpdateRequest;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserUpdateService {


    @Autowired
    UserRepository userRepository;

    public UserDTO update(Long id, UserUpdateRequest userUpdateRequest){
        var user = userUpdateRequest.parseToEntity();
        user.setId(id);
        return userRepository.save(user).parseToDTO();
    }
}

package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.exception.BadRequestException;
import br.com.pitang.user.car.api.exception.NotFoundException;
import br.com.pitang.user.car.api.model.dto.UserDTO;
import br.com.pitang.user.car.api.model.request.user.UserUpdateRequest;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
public class UserUpdateService {


    @Autowired
    UserRepository userRepository;

    @Transactional
    public UserDTO update(Long id, UserUpdateRequest userUpdateRequest){

        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User id:"+ id +" Not Found"));

        if (Objects.nonNull(userUpdateRequest.getFistName()) && !userUpdateRequest.getFistName().equals(user.getFistName())){
            user.setFistName(userUpdateRequest.getFistName());
        }
        if (Objects.nonNull(userUpdateRequest.getLastName()) && !userUpdateRequest.getLastName().equals(user.getLastName())){
            user.setLastName(userUpdateRequest.getLastName());
        }
        if (Objects.nonNull(userUpdateRequest.getEmail()) && !userUpdateRequest.getEmail().equals(user.getEmail())){

            userRepository.findByEmail(userUpdateRequest.getEmail()).ifPresent(email -> {
                throw new BadRequestException("Email already exists");
            });

            user.setEmail(userUpdateRequest.getEmail());
        }
        if (Objects.nonNull(userUpdateRequest.getBirtday()) &&  !userUpdateRequest.getBirtday().equals(user.getBirtday())){
            user.setBirtday(userUpdateRequest.getBirtday());
        }
        if (Objects.nonNull(userUpdateRequest.getPhone()) && !userUpdateRequest.getPhone().equals(user.getPhone())){
            user.setPhone(userUpdateRequest.getPhone());
        }
        if (Objects.nonNull(userUpdateRequest.getPassword()) && !userUpdateRequest.getPassword().equals(user.getPassword())){
            user.setPassword(userUpdateRequest.getPassword());
        }
        if (Objects.nonNull(userUpdateRequest.getLogin()) && !userUpdateRequest.getLogin().equals(user.getLastName())) {

            userRepository.findByLogin(userUpdateRequest.getLogin()).ifPresent(login -> {
                throw new BadRequestException("Login already exists");
            });

            user.setUsername(userUpdateRequest.getLogin());
        }

        return userRepository.save(user).parseToDTO();
    }
}
package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.exception.BadRequestException;
import br.com.pitang.user.car.api.exception.NotFoundException;
import br.com.pitang.user.car.api.model.dto.UserDTO;
import br.com.pitang.user.car.api.model.request.user.UserUpdateRequest;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;


@Service
@Transactional
public class UserUpdateService {


    @Autowired
    private UserRepository userRepository;

    public UserDTO update(Long id, UserUpdateRequest userUpdateRequest){

        var user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User id: "+ id +" not found"));

        if (Objects.nonNull(userUpdateRequest.getFirstName()) && !userUpdateRequest.getFirstName().equals(user.getFirstname())){
            user.setFirstname(userUpdateRequest.getFirstName());
        }
        if (Objects.nonNull(userUpdateRequest.getLastName()) && !userUpdateRequest.getLastName().equals(user.getLastname())){
            user.setLastname(userUpdateRequest.getLastName());
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
        if (Objects.nonNull(userUpdateRequest.getLogin()) && !userUpdateRequest.getLogin().equals(user.getUsername())) {

            userRepository.findByLogin(userUpdateRequest.getLogin()).ifPresent(login -> {
                throw new BadRequestException("Login already exists");
            });

            user.setUsername(userUpdateRequest.getLogin());
        }
        return userRepository.save(user).parseToDTO();
    }

    public UserDTO updatePhoto(MultipartFile photo, Long userId) throws IOException {

        var user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.setPhoto(photo.getBytes());
        return userRepository.save(user).parseToDTO();
    }
}
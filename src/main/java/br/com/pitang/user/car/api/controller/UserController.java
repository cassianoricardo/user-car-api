package br.com.pitang.user.car.api.controller;

import br.com.pitang.user.car.api.model.dto.UserDTO;
import br.com.pitang.user.car.api.model.request.user.UserCreateRequest;
import br.com.pitang.user.car.api.model.request.user.UserUpdateRequest;
import br.com.pitang.user.car.api.service.user.UserCreateService;
import br.com.pitang.user.car.api.service.user.UserDeleteService;
import br.com.pitang.user.car.api.service.user.UserFindService;
import br.com.pitang.user.car.api.service.user.UserUpdateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PathVariable;



import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@Validated
@RequestMapping("users")
public class UserController {

    @Autowired
    UserUpdateService userUpdateService;

    @Autowired
    UserCreateService userCreateService;

    @Autowired
    UserFindService userFindService;

    @Autowired
    UserDeleteService userDeleteService;

    @GetMapping
    public List<UserDTO> getAllUsers(){
        return userFindService.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void createUser(@Valid @RequestBody UserCreateRequest userCreateRequest){
        userCreateService.create(userCreateRequest);
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id){
        return userFindService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteUser(@PathVariable Long id){
        userDeleteService.deleteById(id);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest userUpdateRequest){
        return userUpdateService.update(id, userUpdateRequest);
    }
}
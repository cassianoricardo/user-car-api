package br.com.pitang.user.car.api.controller;

import br.com.pitang.user.car.api.model.dto.UserDTO;
import br.com.pitang.user.car.api.model.dto.request.UserCreateRequest;
import br.com.pitang.user.car.api.model.dto.request.UserUpdateRequest;
import br.com.pitang.user.car.api.service.user.UserCreateService;
import br.com.pitang.user.car.api.service.user.UserDeleteService;
import br.com.pitang.user.car.api.service.user.UserFindService;
import br.com.pitang.user.car.api.service.user.UserUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

@Controller
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("users")
public class UserController extends BaseController{

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
    public void createUser(@RequestBody UserCreateRequest userCreateRequest){
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
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest){
        return userUpdateService.update(id, userUpdateRequest);
    }
}
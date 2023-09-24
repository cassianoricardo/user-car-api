package br.com.pitang.user.car.api.controller;

import br.com.pitang.user.car.api.model.dto.UserDTO;
import br.com.pitang.user.car.api.model.request.user.UserCreateRequest;
import br.com.pitang.user.car.api.model.request.user.UserUpdateRequest;
import br.com.pitang.user.car.api.service.user.UserCreateService;
import br.com.pitang.user.car.api.service.user.UserDeleteService;
import br.com.pitang.user.car.api.service.user.UserFindService;
import br.com.pitang.user.car.api.service.user.UserUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @Operation(summary = "Return all users")
    public List<UserDTO> getAllUsers(){
        return userFindService.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create a new user")
    public void createUser(@Valid @RequestBody UserCreateRequest userCreateRequest){
        userCreateService.create(userCreateRequest);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Return user")
    public UserDTO getUser(@PathVariable Long id){
        return userFindService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete user")
    public void deleteUser(@PathVariable Long id){
        userDeleteService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user data")
    public UserDTO updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest userUpdateRequest){
        return userUpdateService.update(id, userUpdateRequest);
    }
}
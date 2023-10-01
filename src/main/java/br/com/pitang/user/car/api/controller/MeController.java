package br.com.pitang.user.car.api.controller;

import br.com.pitang.user.car.api.model.response.MeResponse;
import br.com.pitang.user.car.api.service.me.MeInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping(path = "me", produces = APPLICATION_JSON_VALUE)
public class MeController {

    @Autowired
    MeInfoService meInfoService;

    @GetMapping
    @Operation(summary = "Returns logged in user information")
    public MeResponse getInfo(){
        return meInfoService.getInfo();
    }
}
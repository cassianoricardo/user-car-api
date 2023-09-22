package br.com.pitang.user.car.api.controller;

import br.com.pitang.user.car.api.model.response.MeResponse;
import br.com.pitang.user.car.api.service.me.MeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("me")
public class MeController {

    @Autowired
    MeInfoService meInfoService;

    @GetMapping
    public MeResponse getInfo(){
        return meInfoService.getInfo();
    }
}
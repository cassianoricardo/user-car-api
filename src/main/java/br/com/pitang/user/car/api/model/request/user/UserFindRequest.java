package br.com.pitang.user.car.api.model.request.user;


import br.com.pitang.user.car.api.service.user.UserCreateService;
import br.com.pitang.user.car.api.service.user.UserUpdateService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFindRequest {

    @Autowired
    UserUpdateService userUpdateService;

    @Autowired
    UserCreateService userCreateService;
}

package br.com.pitang.user.car.api.service.user;

import br.com.pitang.user.car.api.model.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserLoggedService {

    public static User getUserAuthenticated(){
        var securityContext = SecurityContextHolder.getContext();
        return (User) securityContext.getAuthentication().getPrincipal();
    }

}

package br.com.pitang.user.car.api.controller;

import br.com.pitang.user.car.api.controller.response.JwtResponse;
import br.com.pitang.user.car.api.exception.ForbiddenException;
import br.com.pitang.user.car.api.jwt.JwtUtils;
import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.model.request.login.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    try {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtUtils.generateJwtToken(authentication);

      var user = (User) authentication.getPrincipal();
      List<String> roles = user.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .collect(Collectors.toList());

      return ResponseEntity.ok(new JwtResponse(jwt,
              user.getId(),
              user.getUsername(),
              user.getEmail(),
              roles));
    } catch (AuthenticationException ex) {
      throw new ForbiddenException("Invalid login or password");
    }
  }

}
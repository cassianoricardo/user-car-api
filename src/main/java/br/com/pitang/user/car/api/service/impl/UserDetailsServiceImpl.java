package br.com.pitang.user.car.api.service.impl;

import br.com.pitang.user.car.api.model.entity.User;
import br.com.pitang.user.car.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public User loadUserByUsername(String login){
    return userRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User Not Found with login: " + login));
  }

}
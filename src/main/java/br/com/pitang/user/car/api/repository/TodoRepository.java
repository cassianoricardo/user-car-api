/*
package br.com.pitang.user.car.api.repository;

import br.com.pitang.user.car.api.enums.StatusTaskEnum;
import br.com.pitang.user.car.api.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Optional<List<Todo>> findByUserOrderByStatusDesc(User user);

    Optional<Todo> findByIdAndUser(Long id, User user);

    Optional<Todo> deleteByIdAndUser(Long id, User user);
    Optional<List<Todo>> findByStatusAndUserOrderByStatusDesc(StatusTaskEnum status, User user);



}*/

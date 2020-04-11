package ru.veretennikov.todolist.persist.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.veretennikov.todolist.persist.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

//    здесь лучше примитивный тип, чтобы не было null
    boolean existsByUsername(String username);

//    лучше через Optional<>, чтобы null мы могли обработать
    Optional<User> findByUsername(String username);
//    Optional<User> getUserByUsername(String username);

}

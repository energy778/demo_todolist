package ru.veretennikov.todolist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.veretennikov.todolist.persist.entity.User;
import ru.veretennikov.todolist.persist.repo.UserRepository;
import ru.veretennikov.todolist.repr.UserRepr;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional          // работаем с БД
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * получаем данные со слоя repr, создаем объект и перекладываем его в таблицу БД
    */
    public void create(UserRepr userRepr){

        User user = new User();
//        user.setId();                             // сгенерируется spring data
        user.setUsername(userRepr.getUsername());
        user.setPassword(passwordEncoder.encode(userRepr.getPassword()));

        repository.save(user);

    }

    public Optional<User> getCurrentUser(Authentication authentication){

        Object principal = authentication.getPrincipal();

        if (principal instanceof String){
            try{
                Long userId = Long.parseLong((String) principal);
                return repository.findById(userId);
            } catch (Exception ignored){}
        } else if (principal instanceof org.springframework.security.core.userdetails.User){
            try{
                String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
                return repository.getUserByUsername(username);
            } catch (Exception ignored){}
        }

        return Optional.empty();

    }

}

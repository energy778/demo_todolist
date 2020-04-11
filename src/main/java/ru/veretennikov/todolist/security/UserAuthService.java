package ru.veretennikov.todolist.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.veretennikov.todolist.persist.entity.User;
import ru.veretennikov.todolist.persist.repo.UserRepository;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

/**
 * сервис представляет собой реализацию UserDetailsService и конкретно метода загрузки данных пользователя из БД по имени
 */
@Service
@Transactional      // спорный момент. ведь у нас тут только чтение
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> optUser = repository.getUserByUsername(username);
        if (!optUser.isPresent()){
            throw new UsernameNotFoundException("User not found");
        }

        User user = optUser.get();

//        создаем user специального класса из пакета spring security, который реализует интерфейс UserDetails
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
//                так как ролей пока нет, добавим фиктивную роль USER
//                но вообще если в приложении много пользователей, то обычно подразумевается таблица ролей
                Collections.singletonList(new SimpleGrantedAuthority("USER"))
        );

    }

}

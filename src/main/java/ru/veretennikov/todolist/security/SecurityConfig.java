package ru.veretennikov.todolist.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration          // т.к. будем описывать здесь бины
@EnableWebSecurity      // говорит о том, что мы хотим использовать WebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    непонятно, почему выбран именно WebSecurityConfigurerAdapter, есть ли альтернативы?

//    сервис для извлечения пользователей из бд для авторизации
    private final UserDetailsService userAuthService;
//    кодировщик паролей
    private final PasswordEncoder passwordEncoder;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userAuthService);
        auth.setPasswordEncoder(passwordEncoder);       // чтобы не работать с паролем в открытом виде
        return auth;
    }

    /**
     * устанавливаем провайдер, в котором определяем, каким образом должен происходить механизм авторизации пользователей
     *      в нашем случае проверяем в БД, есть ли пользователь с таким username
     * а также "шифруем" пароль
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO: 011 11.04.20 почему только (и именно) этот метод вызываем?
        auth.authenticationProvider(this.authenticationProvider());
    }

    /**
     * реализует логику, как нужно защищать наше приложение от неавторизованного доступа
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/todo/*").authenticated()          // только авторизованные могут видеть главную страницу (список _todo_)
                .antMatchers("/").authenticated()                // только авторизованные могут видеть главную страницу (список _todo_)
                .and()
                .formLogin()                                                // < конфигурируем форму для логина
                .loginPage("/login")                                        // где она расположена
                .loginProcessingUrl("/authenticateTheUser")                 // и по какому адресу она должна отправлять информацию, что пользователь хочет авторизоваться
                .permitAll()                                                // к странице логина имеют доступ все пользователи, которые зашли на сайт >
                .and()
                .logout()                                                   // если есть login, то должен быть и logout. для logout используем конфигурацию по умолчанию
                .logoutSuccessUrl("/login")                                 // после logout должен быть переход на форму login
                .permitAll();

    }

}

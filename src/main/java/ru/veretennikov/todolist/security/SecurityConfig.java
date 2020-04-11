package ru.veretennikov.todolist.security;

import org.springframework.beans.factory.annotation.Autowired;
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
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    не делаем final, потому что будут устанавливаться не через конструктор, а через сеттеры
//    особенность spring security или из-за того, что наследуемся?
// TODO: 011 11.04.20 проверить через конструктор
    private UserDetailsService userAuthService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserAuthService(UserDetailsService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @Autowired
//    а сделал бы ломбок также (как @Autowired), если бы мы использовали @Setter?
    // TODO: 011 11.04.20 проверить
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userAuthService);
        auth.setPasswordEncoder(passwordEncoder);       // чтобы не работать с паролем в открытом виде
        return auth;
    }

    // TODO: 011 11.04.20 почему мы решили переопределить этот метод?
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        в этом методе нужно установить специальный класс, который называется AuthenticationProvider
//        он будет указывать на то, каким образом должен происходить механизм авторизации пользователей
        // TODO: 011 11.04.20 почему только (и именно) этот метод вызываем?
        auth.authenticationProvider(this.authenticationProvider());
    }

    /**
     * реализует логику, как нужно защищать наше приложение от неавторизованного доступа
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/").authenticated()                // только авторизованные
                .and()
                .formLogin()                                                // конфигурируем форму для логина
                .loginPage("/login")
                .loginProcessingUrl("/authenticateTheUser")                 // по какому адресу форма логина должна отправлять информацию, что пользователь хочет авторизоваться
                .permitAll()                                                // к странице логина имеют доступ все пользователи, которые зашли на сайт
                .and()
                .logout()                                                   // если есть login, то должен быть и logout. для logout используем конфигурацию по умолчанию
                .logoutSuccessUrl("/login")
                .permitAll();

    }

}

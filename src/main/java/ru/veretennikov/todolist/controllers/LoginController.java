package ru.veretennikov.todolist.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.veretennikov.todolist.repr.UserRepr;
import ru.veretennikov.todolist.service.UserService;

import javax.validation.Valid;

/**
 * регистрация новых пользователей и их вход в систему
* */
@Controller
@RequiredArgsConstructor
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("user", new UserRepr());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") @Valid UserRepr userRepr,
                                  BindingResult bindingResult){

//        @Valid перед аргументом означает, что будут провалидированы все поля объекта (для которых указаны соответствующие аннотации: @NotEmpty, @NotNull и т.п.)
//        BindingResult - тоже валидатор своего рода. Из него мы можем узнать были ли ошибки валидации и какие при байденге http запроса на объект в контроллере

        logger.info("New user {}", userRepr);

        if (bindingResult.hasErrors())
            return "register";

        if (!userRepr.getPassword().equals(userRepr.getMatchingPassword())){
            bindingResult.rejectValue("password", "", "Password not matching!");
            return "register";
        }

        userService.create(userRepr);

//        если пользователь успешно зарегистрировался, то перекидываем его на страницу логина
        return "redirect:/login";

    }

}

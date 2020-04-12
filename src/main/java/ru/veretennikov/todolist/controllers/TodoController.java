package ru.veretennikov.todolist.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.veretennikov.todolist.exception.ResourceNotFoundException;
import ru.veretennikov.todolist.exception.UserNotFoundException;
import ru.veretennikov.todolist.persist.entity.User;
import ru.veretennikov.todolist.repr.TodoRepr;
import ru.veretennikov.todolist.service.TodoService;
import ru.veretennikov.todolist.service.UserService;

import java.util.List;

/**
 * отображение списка дел
 */
@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final UserService userService;

    /**
     * страница списка элементов
     */
    @GetMapping("/")
    public String indexPage(Authentication authentication, Model model){

        User user = userService.getCurrentUser(authentication).orElseThrow(UserNotFoundException::new);
        List<TodoRepr> todoByUserId = todoService.findTodoByUserId(user.getId());
        model.addAttribute("todo", todoByUserId);
        return "index";

    }

    /**
     * переходим на страницу редактирования конкретного todoItem со страницы списка элементов
     */
    @GetMapping("/todo/{id}")
    public String todoPage(@PathVariable("id") Long id, Model model){

        TodoRepr todoRepr = todoService.findById(id).orElseThrow(ResourceNotFoundException::new);
        model.addAttribute("todo", todoRepr);
        return "todo";

    }

    /**
     * переходим на страницу добавления нового todoItem со страницы списка элементов
     */
    @GetMapping("/todo/create")
    public String createPage(Model model){

        model.addAttribute("todo", new TodoRepr());
        return "todo";

    }

    /**
     * добавление нового / сохранение отредактированного todoItem
     */
    @PostMapping("/todo/create")
    public String createTodo(@ModelAttribute("todo") TodoRepr todoRepr, Authentication authentication){

        User user = userService.getCurrentUser(authentication).orElseThrow(UserNotFoundException::new);
        todoService.save(todoRepr, user);
        return "redirect:/";

    }

    /**
     * удаление todoItem
     */
    @GetMapping("/todo/delete/{id}")
    public String saveTodo(@PathVariable("id") Long id){

        todoService.remove(id);
        return "redirect:/";

    }

}

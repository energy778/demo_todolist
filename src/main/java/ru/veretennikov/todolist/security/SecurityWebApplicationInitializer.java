package ru.veretennikov.todolist.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.stereotype.Component;

/**
 * такая странная пустая реализация класса как я понимаю позволяет поднимать security context (инициализировать бины spring security)
 */
@Component
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
}

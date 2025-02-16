package com.henilveira.sonita.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CookieUtil {

    public static void addJwtCookie(HttpServletResponse response, String jwtToken) {
        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setHttpOnly(true); // 🔹 Torna o cookie inacessível via JavaScript
        cookie.setSecure(true);   // 🔹 Garante que seja enviado apenas em HTTPS
        cookie.setPath("/");      // 🔹 Disponível em toda a aplicação
        cookie.setMaxAge(3600);   // 🔹 Expira em 1 hora
        cookie.setDomain("localhost"); // 🔹 Define o domínio permitido

        response.addCookie(cookie);
    }
}
package com.henilveira.sonita.domain.auth;

import com.henilveira.sonita.domain.user.UserRoles;

public record RegisterDTO(String email, String password, UserRoles role) {
}

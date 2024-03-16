package com.bookstore.Bookstore.domains.repository;

import com.bookstore.Bookstore.domains.dto.JwtResponseDto;
import com.bookstore.Bookstore.domains.dto.UserRequestDto;

public interface IAuthRepository {
    JwtResponseDto signIn(UserRequestDto request);

    JwtResponseDto signOut(String jwt);
}

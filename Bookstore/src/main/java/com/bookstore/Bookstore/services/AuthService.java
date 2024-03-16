package com.bookstore.Bookstore.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bookstore.Bookstore.domains.dto.JwtResponseDto;
import com.bookstore.Bookstore.domains.dto.UserRequestDto;
import com.bookstore.Bookstore.domains.models.UserEntity;
import com.bookstore.Bookstore.domains.repository.IAuthRepository;
import com.bookstore.Bookstore.exceptions.PasswordIncorrectException;
import com.bookstore.Bookstore.exceptions.UserNotExistException;
import com.bookstore.Bookstore.security.JwtAuthenticationProvider;

import lombok.RequiredArgsConstructor;

/**
 * Service responsible for logging a user
 */
@RequiredArgsConstructor
@Service
public class AuthService  implements IAuthRepository {

    private final UserService userService;

    /**
     * Class that manages JWTs
     */
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    /**
     * Password encrypting class
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Returns a data with the user's jwt given some credentials
     * @param authCustomerDto Access credentials
     * @return Dto with the user's jwt if the credentials are valid
     */
    @Override
    public JwtResponseDto signIn(UserRequestDto authCustomerDto) {

        UserEntity customer = userService.getByEmail(authCustomerDto.getEmail());
        
        if (customer == null) {
            throw new UserNotExistException();
        }

        if (!passwordEncoder.matches(authCustomerDto.getPassword(), customer.getPassword())) {
            throw new PasswordIncorrectException();
        }


        return new JwtResponseDto(jwtAuthenticationProvider.createToken(customer));
    }


    /**
     * Close the session by removing the entered token from the whitelist
     * @param token Token to delete
     * @return
     */
    @Override
    public JwtResponseDto signOut(String token) {
        String[] authElements = token.split(" ");
        String response = jwtAuthenticationProvider.deleteToken(authElements[1]);
        return new JwtResponseDto(response);
    }

}


package com.bookstore.Bookstore.security;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import javax.security.sasl.AuthenticationException;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bookstore.Bookstore.domains.models.UserEntity;

/**
 * Class in charge of creating and validating jwt for login
 * of a User.
 */
@Component
public class JwtAuthenticationProvider {

    /**
     * Llave para cifrar el jwt
     */
    // @Value("${jwt.secret.key}")
    private String secretKey = "secret";

    /**
     * List intended to save the jwt's serving as the state of the application.
     */
    private HashMap<String, UserEntity> listToken = new HashMap<>();

    /**
     * Creates a new jwt based on the client received by parameter and adds it 
     * to the state list.
     * 
     * @param customerJwt Client to use in the creation of the jwt
     * @return Created Jwt 
     */
    public String createToken(UserEntity customerJwt) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hora en milisegundos

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        String tokenCreated = JWT.create()
                .withClaim("id", customerJwt.getId())
                .withClaim("name", customerJwt.getName())
                .withClaim("lastName", customerJwt.getLastName())
                .withClaim("Phone", customerJwt.getPhone())
                .withClaim("email", customerJwt.getEmail())
                .withClaim("rol", customerJwt.getRole())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);

        listToken.put(tokenCreated, customerJwt);
        return tokenCreated;
    }

    /**
     * Validates if the token is valid and returns a user session
     * 
     * @param token Token to validate
     * @return User session
     * @throws CredentialsExpiredException If the token has already expired
     * @throws BadCredentialsException     If the token does not exist in the state list
     */
    public Authentication validateToken(String token) throws AuthenticationException {

        System.out.println("entre tambien aqui");
        System.out.println(token);

        // verifies the token as its signature and expiration, throws an exception if something fails.
        JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);

        UserEntity exists = listToken.get(token);
        if (exists == null) {
            throw new BadCredentialsException("Usuario no registrado.");
        }

        // Add the user to the State list.
        HashSet<SimpleGrantedAuthority> rolesAndAuthorities = new HashSet<>();
        rolesAndAuthorities.add(new SimpleGrantedAuthority("ROLE_" + exists.getRole())); 

        // Return the User Session.
        return new UsernamePasswordAuthenticationToken(exists, token, rolesAndAuthorities);
    }


    /**
     * Delete token from the current State list and returns a String with the status.
     * 
     * @param jwt Token to remove
     * @return String Status
     */
    public String deleteToken(String jwt) {

        if (!listToken.containsKey(jwt)) {
            return "No existe token";
        }

        listToken.remove(jwt);
        return "Sesión cerrada exitosamente";
    }

}
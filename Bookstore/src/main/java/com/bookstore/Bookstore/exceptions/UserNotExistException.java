package com.bookstore.Bookstore.exceptions;

public class UserNotExistException extends RuntimeException{
    public UserNotExistException(){
        super("El usuario ingresado no existe.");
    }
}

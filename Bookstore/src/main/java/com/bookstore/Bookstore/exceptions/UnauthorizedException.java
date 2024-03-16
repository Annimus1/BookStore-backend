package com.bookstore.Bookstore.exceptions;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(){
        super("Don't have enough permissions.");
    }
}

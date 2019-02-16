package com.bfamily.exceptions;

public final class BadCredentialsException extends Exception
{
    public BadCredentialsException(String message)
    {
        super(message);
    }
}
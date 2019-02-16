package org.libra.exceptions;

public final class AccessDeniedException extends Exception
{
    public AccessDeniedException(String message)
    {
        super(message);
    }
}
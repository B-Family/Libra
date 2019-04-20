package org.libra.exceptions;

public final class SelfDestructionException extends Exception
{
    public SelfDestructionException(String message)
    {
        super(message);
    }
}
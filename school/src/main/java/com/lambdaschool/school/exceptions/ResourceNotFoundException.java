package com.lambdaschool.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException
{
    public ResourceNotFoundException(String message)
    {
        super(message + " Please try again.");

    }

    public ResourceNotFoundException(String message, Throwable cause)
    {
        super(message + " Please try again.", cause);

    }

}

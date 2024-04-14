package com.wilson.user.test;

import com.wilson.user.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Resource not found exception test.
 */
public class ResourceNotFoundExceptionTest {

    /**
     * When resource not found exception message then throw.
     */
    @Test
    public void whenResourceNotFoundExceptionMessageThenThrow() {
        String resourceName = "User";
        String fieldName = "id";
        Long fieldValue = 1L;

        ResourceNotFoundException exception = new ResourceNotFoundException(resourceName, fieldName, fieldValue);

        String expectedMessage = String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue);
        assertEquals(expectedMessage, exception.getMessage());
    }
}
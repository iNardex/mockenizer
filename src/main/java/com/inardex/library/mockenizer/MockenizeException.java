package com.inardex.library.mockenizer;

/**
 * Exception thrown by mockenizer library when encountered an error.
 */
public class MockenizeException extends RuntimeException {

    MockenizeException(String message) {
        super(message);
    }

}

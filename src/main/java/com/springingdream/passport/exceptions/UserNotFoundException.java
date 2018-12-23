package com.springingdream.passport.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long uid) {
        super("No user with uid: " + uid);
    }
}

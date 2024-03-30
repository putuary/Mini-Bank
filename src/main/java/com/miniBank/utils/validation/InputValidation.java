package com.miniBank.utils.validation;

public class InputValidation {
    public static void UsernameValidation (String username){
        if(!(username != null && username.length() >= 3 && username.length() <= 50)) {
            throw new IllegalArgumentException("Invalid username");
        }
    }
    public static void PasswordValidation (String password){
        if (!(password != null && password.length() >= 3 && password.length() <= 30)) {
            throw new IllegalArgumentException("Invalid password");
        }
    }
}

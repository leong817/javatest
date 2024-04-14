package com.wilson.user.service;

import com.wilson.user.model.UserRecord;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * The type Validation service.
 */
@Service
public class ValidationService {
    private static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    /**
     * Validate user boolean.
     *
     * @param userRecord the user record
     * @return the boolean
     */
    public boolean validateUser(UserRecord userRecord) {
        return isValidName(userRecord.getName()) && isValidEmail(userRecord.getEmail());
    }

    /**
     * Is valid name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    protected boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    /**
     * Is valid email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    protected boolean isValidEmail(String email) {
        return email != null && Pattern.matches(EMAIL_PATTERN, email);
    }
}
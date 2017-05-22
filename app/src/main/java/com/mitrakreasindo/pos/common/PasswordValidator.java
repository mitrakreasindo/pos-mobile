package com.mitrakreasindo.pos.common;

/**
 * Created by hendric on 2017-05-22.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator
{
    private Pattern pattern;
    private Matcher matcher;

    /**
     * ^                 # start-of-string
     * (?=.*[0-9])       # a digit must occur at least once
     * (?=.*[a-z])       # a lower case letter must occur at least once
     * (?=.*[A-Z])       # an upper case letter must occur at least once
     * (?=.*[@#$%^&+=])  # a special character must occur at least once
     * (?=\S+$)          # no whitespace allowed in the entire string
     * .{8,10}           # at least eight chars & max 10 chars
     * $                 # end-of-string
     */

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9])(?=\\S+$).{8,10}$";

    public PasswordValidator()
    {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    /**
     * Validate password with regular expression
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public boolean validate(final String password)
    {
        matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
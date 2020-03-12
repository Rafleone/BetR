package bet.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static final String NAME_REGEX_PATTERN = "^[a-zA-z0-9]{5,12}$";
    public static final String USER_PASSWORD_REGEX_PATTERN = "^[a-zA-z0-9@!?_#&%.^]{5,12}$";
    public static final String USER_EMAIL_REGEX_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,50}$";
    public static final String AGE_REGEX_PATTERN = "^[0-9]{2}$";

    public static boolean isValidName(String name) {
        Pattern pattern = Pattern.compile(NAME_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(USER_PASSWORD_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public static boolean isValidEmail(String regEmail) {
        Pattern pattern = Pattern.compile(USER_EMAIL_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(regEmail);
        return matcher.find();
    }

    public static boolean isValidAge(String age1){
        Pattern pattern = Pattern.compile(AGE_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(age1);
        return matcher.find();
    }
}

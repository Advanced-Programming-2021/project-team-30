package model.regex;

public class RegisterMenuRegex {
    public static String[] registerUser = {
            "user create --username (?<username>.*) --nickname (?<nickname>.*) --password (?<password>.*)",
            "user create --username (?<username>.*) --password (?<password>.*) --nickname (?<nickname>.*)",
            "user create --password (?<password>.*) --username (?<username>.*) --nickname (?<nickname>.*)",
            "user create --password (?<password>.*) --nickname (?<nickname>.*) --username (?<username>.*)",
            "user create --nickname (?<nickname>.*) --password (?<password>.*) --username (?<username>.*)",
            "user create --nickname (?<nickname>.*) --username (?<username>.*) --password (?<password>.*)"
    };
    public static String[] registerUserAbbr = {
            "user create -u (?<username>.*) -n (?<nickname>.*) -p (?<password>.*)",
            "user create -u (?<username>.*) -p (?<password>.*) -n (?<nickname>.*)",
            "user create -p (?<password>.*) -u (?<username>.*) -n (?<nickname>.*)",
            "user create -p (?<password>.*) -n (?<nickname>.*) -u (?<username>.*)",
            "user create -n (?<nickname>.*) -p (?<password>.*) -u (?<username>.*)",
            "user create -n (?<nickname>.*) -u (?<username>.*) -p (?<password>.*)"
    };
    public static String[] loginUser = {
            "user login --username (?<username>.*) --password (?<password>.*)",
            "user login --password (?<password>.*) --username (?<username>.*)"
    };
    public static String[] loginUserAbbr = {
            "user login -u (?<username>.*) -p (?<password>.*)",
            "user login -p (?<password>.*) -u (?<username>.*)"
    };


}

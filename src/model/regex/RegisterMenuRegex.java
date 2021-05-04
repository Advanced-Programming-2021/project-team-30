package model.regex;

public class RegisterMenuRegex {
    public static String[] registerUser = {
            "\\s*user create --username \\s*(?<username>.*)\\s* --nickname \\s*(?<nickname>.*)\\s* --password \\s*(?<password>.*)\\s*",
            "\\s*user create --username \\s*(?<username>.*)\\s* --password \\s*(?<password>.*)\\s* --nickname \\s*(?<nickname>.*)\\s*",
            "\\s*user create --password \\s*(?<password>.*)\\s* --username \\s*(?<username>.*)\\s* --nickname \\s*(?<nickname>.*)\\s*",
            "\\s*user create --password \\s*(?<password>.*)\\s* --nickname \\s*(?<nickname>.*)\\s* --username \\s*(?<username>.*)\\s*",
            "\\s*user create --nickname \\s*(?<nickname>.*)\\s* --password \\s*(?<password>.*)\\s* --username \\s*(?<username>.*)\\s*",
            "\\s*user create --nickname \\s*(?<nickname>.*)\\s* --username \\s*(?<username>.*)\\s* --password \\s*(?<password>.*)\\s*"
    };
    public static String[] registerUserAbbr = {
            "\\s*user create -u \\s*(?<username>.*)\\s* -n \\s*(?<nickname>.*)\\s* -p \\s*(?<password>.*)\\s*",
            "\\s*user create -u \\s*(?<username>.*)\\s* -p \\s*(?<password>.*)\\s* -n \\s*(?<nickname>.*)\\s*",
            "\\s*user create -p \\s*(?<password>.*)\\s* -u \\s*(?<username>.*)\\s* -n \\s*(?<nickname>.*)\\s*",
            "\\s*user create -p \\s*(?<password>.*)\\s* -n \\s*(?<nickname>.*)\\s* -u \\s*(?<username>.*)\\s*",
            "\\s*user create -n \\s*(?<nickname>.*)\\s* -p \\s*(?<password>.*)\\s* -u \\s*(?<username>.*)\\s*",
            "\\s*user create -n \\s*(?<nickname>.*)\\s* -u \\s*(?<username>.*)\\s* -p \\s*(?<password>.*)\\s*"
    };
    public static String[] loginUser = {
            "\\s*user login --username \\s*(?<username>.*)\\s* --password \\s*(?<password>.*)\\s*",
            "\\s*user login --password \\s*(?<password>.*)\\s* --username \\s*(?<username>.*)\\s*"
    };
    public static String[] loginUserAbbr = {
            "\\s*user login -u \\s*(?<username>.*)\\s* -p \\s*(?<password>.*)\\s*",
            "\\s*user login -p \\s*(?<password>.*)\\s* -u \\s*(?<username>.*)\\s*"
    };


}

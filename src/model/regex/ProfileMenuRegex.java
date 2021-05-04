package model.regex;

public class ProfileMenuRegex {
    public static String changeNickname = "profile change --nickname \\s*(?<nickname>.*)\\s*";
    public static String changeNicknameAbbr = "profile change -n \\s*(?<nickname>.*)\\s*";
    public static String[] changePassword = {
            "\\s*profile change --password --current \\s*(?<currentPassword>.*)\\s* --new \\s*(?<newPassword>.*)\\s*",
            "\\s*profile change --password --new \\s*(?<newPassword>.*)\\s* --current \\s*(?<currentPassword>.*)\\s*",
            "\\s*profile change --new \\s*(?<newPassword>.*)\\s* --password --current \\s*(?<currentPassword>.*)\\s*",
            "\\s*profile change --new \\s*(?<newPassword>.*)\\s* --current \\s*(?<currentPassword>.*)\\s* --password\\s*",
            "\\s*profile change --current \\s*(?<currentPassword>.*)\\s* --new \\s*(?<newPassword>.*)\\s* --password\\s*",
            "\\s*profile change --current \\s*(?<currentPassword>.*)\\s* --password --new \\s*(?<newPassword>.*)\\s*"
    };
    public static String[] changePasswordAbbr = {
            "\\s*profile change -p -c \\s*(?<currentPassword>.*)\\s* -n \\s*(?<newPassword>.*)\\s*",
            "\\s*profile change -p -n \\s*(?<newPassword>.*)\\s* -c \\s*(?<currentPassword>.*)\\s*",
            "\\s*profile change -n \\s*(?<newPassword>.*)\\s* -p -c \\s*(?<currentPassword>.*)\\s*",
            "\\s*profile change -n \\s*(?<newPassword>.*)\\s* -c \\s*(?<currentPassword>.*)\\s* -p\\s*",
            "\\s*profile change -c \\s*(?<currentPassword>.*)\\s* -n \\s*(?<newPassword>.*)\\s* -p\\s*",
            "\\s*profile change -c \\s*(?<currentPassword>.*)\\s* -p -n \\s*(?<newPassword>.*)\\s*"
    };

}

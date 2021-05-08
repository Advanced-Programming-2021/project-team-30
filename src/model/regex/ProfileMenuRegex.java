package model.regex;

public class ProfileMenuRegex {
    public static String changeNickname = "profile change --nickname (?<nickname>.*)";
    public static String changeNicknameAbbr = "profile change -n (?<nickname>.*)";
    public static String[] changePassword = {
            "profile change --password --current (?<currentPassword>.*) --new (?<newPassword>.*)",
            "profile change --password --new (?<newPassword>.*) --current (?<currentPassword>.*)",
            "profile change --new (?<newPassword>.*) --password --current (?<currentPassword>.*)",
            "profile change --new (?<newPassword>.*) --current (?<currentPassword>.*) --password",
            "profile change --current (?<currentPassword>.*) --new (?<newPassword>.*) --password",
            "profile change --current (?<currentPassword>.*) --password --new (?<newPassword>.*)"
    };
    public static String[] changePasswordAbbr = {
            "profile change -p -c (?<currentPassword>.*) -n (?<newPassword>.*)",
            "profile change -p -n (?<newPassword>.*) -c (?<currentPassword>.*)",
            "profile change -n (?<newPassword>.*) -p -c (?<currentPassword>.*)",
            "profile change -n (?<newPassword>.*) -c (?<currentPassword>.*) -p",
            "profile change -c (?<currentPassword>.*) -n (?<newPassword>.*) -p",
            "profile change -c (?<currentPassword>.*) -p -n (?<newPassword>.*)"
    };

}

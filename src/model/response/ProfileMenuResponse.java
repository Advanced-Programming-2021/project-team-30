package model.response;

public class ProfileMenuResponse {
    public static String changeNickname = "nickname changed successfully!";
    public static String nicknameExists(String nickname){
        return "user with nickname " + nickname + " already exists";
    }
    public static String changePassword = "password changed successfully!";
    public static String passwordInvalid = "current password is invalid";
    public static String enterNewPassword = "please enter a new password";
}

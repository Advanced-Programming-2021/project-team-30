package model.response;

public class RegisterMenuResponse {
    public static String userCreated = "user created successfully!";
    public static String usernameExists(String username){
        return "user with username " + username + " already exists";
    }
    public static String nicknameExists(String nickname){
        return "user with nickname " + nickname + " already exists";
    }
    public static String userLoggedIn = "user logged in successfully!";
    public static String notMatch = "Username and password didn’t match!";
    public static String userLoggedOut = "user logged out successfully!";
}

package model.menu;

import java.util.regex.Matcher;

public class MainMenu {
    private static String currentUser;
    private static String opponentUser;

    public static void setCurrentUser(String currentUser) {
        MainMenu.currentUser = currentUser;
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static String getOpponentUser() {
        return opponentUser;
    }

    private void logout(){

    }

    private void newDuel(Matcher matcher){

    }
    public void run(String command){

    }

}

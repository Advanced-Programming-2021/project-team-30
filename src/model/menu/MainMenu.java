package model.menu;

import controller.Controller;
import model.Menu;
import model.response.MainMenuResponse;
import model.response.RegisterMenuResponse;
import view.Main;

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
        MainMenu.setCurrentUser("");
        Controller.currentMenu = Menu.REGISTER;
        Main.outputToUser(RegisterMenuResponse.userLoggedOut);

    }

    private void newDuel(Matcher matcher){

    }
    public void run(String command){

    }

}

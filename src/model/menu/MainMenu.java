package model.menu;

import controller.Controller;
import model.Menu;
import model.Player;
import model.regex.MainMenuRegex;
import model.regex.MenuRegex;
import model.regex.Regex;
import model.response.MainMenuResponse;
import model.response.MenuResponse;
import model.response.RegisterMenuResponse;
import view.Main;

import java.util.regex.Matcher;

public class MainMenu {
    private static Player currentUser;

    public static void setCurrentUser(Player currentUser) {
        MainMenu.currentUser = currentUser;
    }

    public static Player getCurrentUser() {
        return currentUser;
    }


    private void logout(){
        setCurrentUser(null);
        Controller.currentMenu = Menu.REGISTER;
        Main.outputToUser(RegisterMenuResponse.userLoggedOut);
    }

    public void run(String command){
        if (Regex.getCommandMatcher(command, MainMenuRegex.logout).find())
            logout();
        else if (MenuRegex.isNotNavigationCommand(command))
            Main.outputToUser(MenuResponse.invalidCommand);

    }

}

package controller;

import model.Menu;
import model.menu.*;
import model.regex.MenuRegex;
import model.regex.Regex;
import model.response.MenuResponse;
import view.Main;

import java.util.Scanner;

public class Controller {
    public static Menu currentMenu = Menu.REGISTER;
    public RegisterMenu registerMenu = new RegisterMenu();
    public MainMenu mainMenu = new MainMenu();
    public DuelMenu duelMenu;
    public DeckMenu deckMenu;
    public ScoreboardMenu scoreboardMenu;
    public ShopMenu shopMenu;
    public ProfileMenu profileMenu;
    public ImportExportMenu importExportMenu;
    public void createMenus(){
        if (currentMenu == Menu.DUEL)
            duelMenu = new DuelMenu();
        else if (currentMenu == Menu.DECK)
            deckMenu = new DeckMenu();
        else if (currentMenu == Menu.SHOP)
            shopMenu = new ShopMenu();
        else if (currentMenu == Menu.SCOREBOARD)
            scoreboardMenu = new ScoreboardMenu();
        else if (currentMenu == Menu.PROFILE)
            profileMenu = new ProfileMenu();
        else if (currentMenu == Menu.IMPORTEXPORT)
            importExportMenu = new ImportExportMenu();
    }
    private void checkNotLogin(String command){
        boolean notLogin = false;
        String[] menus = {"Main", "Deck", "Duel", "Scoreboard", "Profile", "Shop", "Import/Export"};
        if (Regex.getCommandMatcher(command, MenuRegex.enterMenu).find()){
            String menuName = Regex.getCommandMatcher(command, MenuRegex.enterMenu).group(1);
            for (String menu : menus) {
                if (menuName.equals(menu)) {
                    notLogin = true;
                    break;
                }
            }
            if (notLogin)
                Main.outputToUser(MenuResponse.loginFirst);
        }
    }
    public Menu stringToMenu(String menuName){
        if (menuName.equals("Deck"))
            return Menu.DECK;
        else if (menuName.equals("Duel"))
            return Menu.DUEL;
        else if (menuName.equals("Scoreboard"))
            return Menu.SCOREBOARD;
        else if (menuName.equals("Profile"))
            return Menu.PROFILE;
        else if (menuName.equals("Shop"))
            return Menu.SHOP;
        return Menu.IMPORTEXPORT;
    }
    public void navigate(String command){
        checkNotLogin(command);
        String[] menus = {"Deck", "Duel", "Scoreboard", "Profile", "Shop", "Import/Export"};
        if (Regex.getCommandMatcher(command, MenuRegex.enterMenu).find()) {
            if (currentMenu == Menu.MAIN) {
                String menuName = Regex.getCommandMatcher(command, MenuRegex.enterMenu).group(1);
                for (String menu : menus) {
                    if (menuName.equals(menu)) {
                        currentMenu = stringToMenu(menuName);
                        break;
                    }
                }
            } else if (currentMenu != Menu.REGISTER){
                Main.outputToUser(MenuResponse.menuNavigation);
            }
        } else if (command.matches(MenuRegex.exitMenu)) {
            if (currentMenu == Menu.REGISTER)
                currentMenu = Menu.EXIT;
            else if (currentMenu == Menu.MAIN)
                currentMenu = Menu.REGISTER;
            else
                currentMenu = Menu.MAIN;
        } else if (command.matches(MenuRegex.showCurrent)){
            if (currentMenu == Menu.REGISTER)
                Main.outputToUser("Login Menu");
            else
                Main.outputToUser("Main Menu");

        }
    }


    public void run(){
        while (currentMenu != Menu.EXIT){
            String command = Main.getInput();
            navigate(command);
            if (currentMenu == Menu.REGISTER) {
                registerMenu.run(command);
            } else if (currentMenu == Menu.MAIN) {
                mainMenu.run(command);
                createMenus();
            } else if(currentMenu == Menu.DUEL){
                duelMenu.run(command);
            } else if (currentMenu == Menu.DECK) {
                deckMenu.run(command);
            } else if (currentMenu == Menu.SHOP) {
                shopMenu.run(command);
            } else if (currentMenu == Menu.SCOREBOARD) {
                scoreboardMenu.showScoreboard(command);
            } else if (currentMenu == Menu.PROFILE) {
                profileMenu.run(command);
            } else if (currentMenu == Menu.IMPORTEXPORT) {
                importExportMenu.run(command);
            }
        }

    }
}

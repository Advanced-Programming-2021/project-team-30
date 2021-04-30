package controller;

import model.Menu;
import model.menu.*;

import java.util.Scanner;

public class Controller {
    public static Menu currentMenu;
    public static Scanner scanner;
    public RegisterMenu registerMenu;
    public MainMenu mainMenu;
    public DuelMenu duelMenu;
    public DeckMenu deckMenu;
    public ScoreboardMenu scoreboardMenu;
    public ShopMenu shopMenu;
    public ProfileMenu profileMenu;
    public ImportExportMenu importExportMenu;

    public boolean run(String command){
        return true;

    }
}

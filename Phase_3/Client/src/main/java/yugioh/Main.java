package yugioh;

import yugioh.controller.MainController;
import yugioh.controller.MainMenuController;
import yugioh.model.CardInitializer;
import yugioh.model.Player;
import yugioh.view.LoginMenuView;

public class Main{
    public static void main(String[] args) {
        Player.readPlayers();
        MainController.initializeNetwork();
        LoginMenuView.main(args);
    }
}

package yugioh;

import yugioh.controller.MainController;
import yugioh.controller.MainMenuController;
import yugioh.model.CardInitializer;
import yugioh.model.Player;
import yugioh.view.LoginMenuView;

import java.util.ArrayList;

public class Main{
    public static ArrayList<Player> players = new ArrayList<>();
    public static void main(String[] args) {
        //Player.readPlayers();
        MainController.initializeNetwork();
        LoginMenuView.main(args);
    }

    public static Player getPlayerByUsername(String username){
        for(Player player: players)
            if(player.getUsername().equals(username))
                return player;
        return null;
    }
}

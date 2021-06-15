package yugioh;

import yugioh.model.Player;
import yugioh.view.LoginMenuView;

public class Main{
    public static void main(String[] args) {
        Player.readPlayers();
        LoginMenuView.main(args);
        Player.writePlayers();
    }
}

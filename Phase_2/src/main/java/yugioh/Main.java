package yugioh;

import yugioh.model.CardInitializer;
import yugioh.model.Player;
import yugioh.view.LoginMenuView;

public class Main{
    public static void main(String[] args) {
        CardInitializer.readCardsFromCSV();
        Player.readPlayers();
        LoginMenuView.main(args);
        Player.writePlayers();
    }
}

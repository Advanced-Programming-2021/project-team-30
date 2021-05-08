package model.menu;

import model.Deck;
import model.Duel;
import model.Player;
import model.regex.DuelMenuRegex;
import model.regex.MenuRegex;
import model.regex.Regex;
import model.response.DuelMenuResponse;
import model.response.MenuResponse;
import view.Main;

import java.util.regex.Matcher;

public class DuelMenu {
    Player currentPlayer;

    public DuelMenu(){
        currentPlayer = MainMenu.getCurrentUser();
    }
    private void newDuel(Matcher matcher){
        if (matcher.find()){
            String player2Username = matcher.group("player2Name");
            int round = Integer.parseInt(matcher.group("round"));
            Player opponentPlayer = Player.getPlayerByUsername(player2Username);
            if (opponentPlayer == null)
                Main.outputToUser(DuelMenuResponse.noPlayer);
            else {
                Deck currentPlayerDeck = currentPlayer.getActiveDeck();
                Deck opponentPlayerDeck = opponentPlayer.getActiveDeck();
                if (currentPlayerDeck == null)
                    Main.outputToUser(DuelMenuResponse.hasNoActiveDeck(currentPlayer.getUsername()));
                else if (opponentPlayerDeck == null)
                    Main.outputToUser(DuelMenuResponse.hasNoActiveDeck(opponentPlayer.getUsername()));
                else if (!currentPlayerDeck.isValid())
                    Main.outputToUser(DuelMenuResponse.hasInvalidDeck(currentPlayer.getUsername()));
                else if (!opponentPlayerDeck.isValid())
                    Main.outputToUser(DuelMenuResponse.hasInvalidDeck(opponentPlayer.getUsername()));
                else if (round != 1 && round != 3)
                    Main.outputToUser(DuelMenuResponse.invalidRound);
                else
                    new Duel(currentPlayer, opponentPlayer, round);

            }

        }

    }
    private void newDuelWithAI(Matcher matcher){
        if (matcher.find()){
            int round = Integer.parseInt(matcher.group("round"));
        }

    }
    public void run(String command){
        Matcher newDuel = Regex.getCommandMatcherRegexes(command, DuelMenuRegex.newDuel);
        Matcher newDuelAbbr = Regex.getCommandMatcherRegexes(command, DuelMenuRegex.newDuelAbbr);
        Matcher newDuelAI = Regex.getCommandMatcherRegexes(command, DuelMenuRegex.newDuelAI);
        Matcher newDuelAIAbbr = Regex.getCommandMatcherRegexes(command, DuelMenuRegex.newDuelAIAbbr);
        if (newDuel != null)
            newDuel(newDuel);
        else if (newDuelAbbr != null)
            newDuel(newDuelAbbr);
        else if (newDuelAI != null)
            newDuelWithAI(newDuelAI);
        else if (newDuelAIAbbr != null)
            newDuelWithAI(newDuelAIAbbr);
        else if (MenuRegex.isNotNavigationCommand(command))
            Main.outputToUser(MenuResponse.invalidCommand);

    }
}

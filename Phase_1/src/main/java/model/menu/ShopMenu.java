package model.menu;

import model.Initializer;
import model.Player;
import model.cards.Card;
import model.regex.DebugRegex;
import model.regex.MenuRegex;
import model.regex.Regex;
import model.regex.ShopMenuRegex;
import model.response.MenuResponse;
import model.response.ShopMenuResponse;
import view.Main;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ShopMenu {
    private Player currentPlayer;
    public static ArrayList<Card> cards;
    static {
        cards = new ArrayList<>();
        Initializer.readCardsFromCSV();
        cards.addAll(Initializer.monsterCards);
        cards.addAll(Initializer.spellTrapCards);
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Card getCardFromCards(String cardName){
        for (Card card : cards) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }
    private void buy(Matcher matcher){
        if (matcher.find()){
            String cardName = matcher.group("cardName");
            Card card = getCardFromCards(cardName);
            if (card == null)
                Main.outputToUser(ShopMenuResponse.noCard);
            else if (getCurrentPlayer().getMoney() < card.getPrice())
                Main.outputToUser(ShopMenuResponse.notEnoughMoney);
            else
                getCurrentPlayer().addCard(card);

        }

    }
    private void showAllCards(){
        DeckMenu.showCards(cards);
    }
    private void increasePlayerMoney(Matcher matcher){
        if (matcher.find()){
            int amount = Integer.parseInt(matcher.group("amount"));
            getCurrentPlayer().setMoney(getCurrentPlayer().getMoney() + amount);
        }
    }
    public void run(String command){
        if (Regex.getCommandMatcher(command, ShopMenuRegex.cardShow).find())
            Card.showCard(Regex.getCommandMatcher(command, ShopMenuRegex.cardShow));
        else if (Regex.getCommandMatcher(command, ShopMenuRegex.buyCard).find())
            buy(Regex.getCommandMatcher(command, ShopMenuRegex.buyCard));
        else if (Regex.getCommandMatcher(command, ShopMenuRegex.showAllCards).find()
                || Regex.getCommandMatcher(command, ShopMenuRegex.showAllCardsAbbr).find())
            showAllCards();
        else if (Regex.getCommandMatcher(command, DebugRegex.increaseMoney).find())
            increasePlayerMoney(Regex.getCommandMatcher(command, DebugRegex.increaseMoney));
        else if (Regex.getCommandMatcher(command, DebugRegex.increaseMoneyAbbr).find())
            increasePlayerMoney(Regex.getCommandMatcher(command, DebugRegex.increaseMoneyAbbr));
        else if (MenuRegex.isNotNavigationCommand(command))
            Main.outputToUser(MenuResponse.invalidCommand);


    }
}

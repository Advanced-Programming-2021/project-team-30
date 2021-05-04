package model.menu;

import model.Player;
import model.cards.Card;
import model.regex.Regex;
import model.regex.ShopMenuRegex;
import model.response.ShopMenuResponse;
import view.Main;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ShopMenu {
    private Player currentPlayer;
    public ArrayList<Card> cards;

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public ShopMenu(){
        Player player = MainMenu.getCurrentUser();
        if (player != null)
            setCurrentPlayer(player);
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
    public void run(String command){
        if (Regex.getCommandMatcher(command, ShopMenuRegex.cardShow).find())
            Card.showCard(Regex.getCommandMatcher(command, ShopMenuRegex.cardShow));
        else if (Regex.getCommandMatcher(command, ShopMenuRegex.buyCard).find())
            buy(Regex.getCommandMatcher(command, ShopMenuRegex.buyCard));
        else if (Regex.getCommandMatcher(command, ShopMenuRegex.showAllCards).find()
                || Regex.getCommandMatcher(command, ShopMenuRegex.showAllCardsAbbr).find())
            showAllCards();

    }
}

package model.menu;

import model.Deck;
import model.Player;
import model.cards.Card;
import model.regex.DeckMenuRegex;
import model.regex.MenuRegex;
import model.regex.Regex;
import model.response.DeckMenuResponse;
import model.response.MenuResponse;
import view.Main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class DeckMenu {
    private Player currentPlayer;

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    private void createDeck(Matcher matcher){
        if (matcher.find()){
            String deckName = matcher.group("deckName");
            if (getCurrentPlayer().getPlayerDeckByName(deckName) != null)
                Main.outputToUser(DeckMenuResponse.deckExists(deckName));
            else{
                getCurrentPlayer().addToDecks(new Deck(deckName, getCurrentPlayer()));
                Main.outputToUser(DeckMenuResponse.deckCreated);
            }
        }

    }
    private void deleteDeck(Matcher matcher){
        if (matcher.find()){
            String deckName = matcher.group("deckName");
            Deck deck = getCurrentPlayer().getPlayerDeckByName(deckName);
            if (deck == null)
                Main.outputToUser(DeckMenuResponse.deckDoesNotExist(deckName));
            else{
                getCurrentPlayer().removeFromDecks(deck);
                Main.outputToUser(DeckMenuResponse.deckDeleted);
            }

        }

    }
    private void setActiveDeck(Matcher matcher){
        if (matcher.find()) {
            String deckName = matcher.group("deckName");
            Deck deck = getCurrentPlayer().getPlayerDeckByName(deckName);
            if (deck == null)
                Main.outputToUser(DeckMenuResponse.deckDoesNotExist(deckName));
            else{
                getCurrentPlayer().setActiveDeck(deck);
                Main.outputToUser(DeckMenuResponse.deckActivated);
            }
        }
    }
    private void addCardMain(Matcher matcher){
        if (matcher.find()){
            String cardName = matcher.group("cardName");
            String deckName = matcher.group("deckName");
            Card card = getCurrentPlayer().getPlayerCardByName(cardName);
            Deck deck = getCurrentPlayer().getPlayerDeckByName(deckName);
            if (card == null)
                Main.outputToUser(DeckMenuResponse.cardDoesNotExist(cardName));
            else if (deck == null)
                Main.outputToUser(DeckMenuResponse.deckDoesNotExist(deckName));
            else if (deck.getMainDeck().size() == 60)
                Main.outputToUser(DeckMenuResponse.mainDeckFull);
            else if (deck.returnCardCountDeck(cardName) == 3 || deck.returnCardCountSide(cardName) == 3)
                Main.outputToUser(DeckMenuResponse.cards3InDeck(cardName, deckName));
            else{
                deck.addCardToMainDeck(card);
                Main.outputToUser(DeckMenuResponse.cardAdded);
            }

        }
    }
    private void addCardSide(Matcher matcher){
        if (matcher.find()){
            String cardName = matcher.group("cardName");
            String deckName = matcher.group("deckName");
            Card card = getCurrentPlayer().getPlayerCardByName(cardName);
            Deck deck = getCurrentPlayer().getPlayerDeckByName(deckName);
            if (card == null)
                Main.outputToUser(DeckMenuResponse.cardDoesNotExist(cardName));
            else if (deck == null)
                Main.outputToUser(DeckMenuResponse.deckDoesNotExist(deckName));
            else if (deck.getMainDeck().size() == 15)
                Main.outputToUser(DeckMenuResponse.sideDeckFull);
            else if (deck.returnCardCountDeck(cardName) == 3 || deck.returnCardCountMain(cardName) == 3)
                Main.outputToUser(DeckMenuResponse.cards3InDeck(cardName, deckName));
            else{
                deck.addCardToSideDeck(card);
                Main.outputToUser(DeckMenuResponse.cardAdded);
            }

        }

    }
    private void removeCardMain(Matcher matcher){
        if (matcher.find()) {
            String cardName = matcher.group("cardName");
            String deckName = matcher.group("deckName");
            Deck deck = getCurrentPlayer().getPlayerDeckByName(deckName);
            if (deck == null)
                Main.outputToUser(DeckMenuResponse.deckDoesNotExist(deckName));
            else{
                Card card = deck.getMainDeckCardByName(cardName);
                if (card == null)
                    Main.outputToUser(DeckMenuResponse.cardDoesNotExist(cardName, true));
                else{
                    deck.removeCardFromMainDeck(card);
                    Main.outputToUser(DeckMenuResponse.cardRemoved);

                }
            }
        }

    }
    private void removeCardSide(Matcher matcher){
        if (matcher.find()) {
            String cardName = matcher.group("cardName");
            String deckName = matcher.group("deckName");
            Deck deck = getCurrentPlayer().getPlayerDeckByName(deckName);
            if (deck == null)
                Main.outputToUser(DeckMenuResponse.deckDoesNotExist(deckName));
            else{
                Card card = deck.getSideDeckCardByName(cardName);
                if (card == null)
                    Main.outputToUser(DeckMenuResponse.cardDoesNotExist(cardName, false));
                else{
                    deck.removeCardFromSideDeck(card);
                    Main.outputToUser(DeckMenuResponse.cardRemoved);
                }
            }
        }

    }

    private void showDecks(){
        ArrayList<Deck> decks = new ArrayList<>(getCurrentPlayer().getDecks());
        StringBuilder string = new StringBuilder("Decks:\nActive deck:\n");
        if (getCurrentPlayer().getActiveDeck() != null){
            string.append(getCurrentPlayer().getActiveDeck().toStringForShowDecks());
        }
        string.append("Other decks:\n");
        Comparator<Deck> deckComparator = Comparator.comparing(Deck::getName);
        List<Deck> sortedDecks = decks.stream().sorted(deckComparator).collect(Collectors.toList());
        for (Deck sortedDeck : sortedDecks) {
            string.append(sortedDeck.toStringForShowDecks());
        }
        Main.outputToUser(string.toString());
    }
    private void showDeck(Matcher matcher, boolean isMain){
        if (matcher.find()){
            String deckName = matcher.group("deckName");
            Deck deck = getCurrentPlayer().getPlayerDeckByName(deckName);
            if (deck == null)
                Main.outputToUser(DeckMenuResponse.deckDoesNotExist(deckName));
            else {
                if (isMain)
                    Main.outputToUser(deck.toStringMainDeck());
                else
                    Main.outputToUser(deck.toStringSideDeck());
            }
        }

    }

    public static void showCards(ArrayList<Card> cards){
        ArrayList<Card> cardsCopy = new ArrayList<>(cards);
        Comparator<Card> cardComparator = Comparator.comparing(Card::getName);
        List<Card> sortedCards = cardsCopy.stream().sorted(cardComparator).collect(Collectors.toList());
        for (Card sortedCard : sortedCards) {
            String string = sortedCard.getName() + ":" + sortedCard.getDetails();
            Main.outputToUser(string);
        }

    }
    private HashMap<String, Matcher> chooseAddRemoveCardMatcher(String command){
        HashMap<String, Matcher> chooseMatcher = new HashMap<>();
        if (Regex.getCommandMatcherRegexes(command, DeckMenuRegex.addCardMain) != null)
            chooseMatcher.put("addCardMain", Regex.getCommandMatcherRegexes(command, DeckMenuRegex.addCardMain));
        if (Regex.getCommandMatcherRegexes(command, DeckMenuRegex.addCardMainAbbr) != null)
            chooseMatcher.put("addCardMainAbbr", Regex.getCommandMatcherRegexes(command, DeckMenuRegex.addCardMainAbbr));
        if (Regex.getCommandMatcherRegexes(command, DeckMenuRegex.addCardSide) != null)
            chooseMatcher.put("addCardSide", Regex.getCommandMatcherRegexes(command, DeckMenuRegex.addCardSide));
        if (Regex.getCommandMatcherRegexes(command, DeckMenuRegex.addCardSideAbbr) != null)
            chooseMatcher.put("addCardSideAbbr", Regex.getCommandMatcherRegexes(command, DeckMenuRegex.addCardSideAbbr));
        if (Regex.getCommandMatcherRegexes(command, DeckMenuRegex.removeCardMain) != null)
            chooseMatcher.put("removeCardMain", Regex.getCommandMatcherRegexes(command, DeckMenuRegex.removeCardMain));
        if (Regex.getCommandMatcherRegexes(command, DeckMenuRegex.removeCardMainAbbr) != null)
            chooseMatcher.put("removeCardMainAbbr", Regex.getCommandMatcherRegexes(command, DeckMenuRegex.removeCardMainAbbr));
        if (Regex.getCommandMatcherRegexes(command, DeckMenuRegex.removeCardSide) != null)
            chooseMatcher.put("removeCardSide", Regex.getCommandMatcherRegexes(command, DeckMenuRegex.removeCardSide));
        if (Regex.getCommandMatcherRegexes(command, DeckMenuRegex.removeCardSideAbbr) != null)
            chooseMatcher.put("removeCardSideAbbr", Regex.getCommandMatcherRegexes(command, DeckMenuRegex.removeCardSideAbbr));
        return chooseMatcher;
    }
    public boolean runAddRemoveCard(String command){

        if (chooseAddRemoveCardMatcher(command).get("addCardMain") != null)
            addCardMain(chooseAddRemoveCardMatcher(command).get("addCardMain"));
        else if (chooseAddRemoveCardMatcher(command).get("addCardMainAbbr") != null)
            addCardMain(chooseAddRemoveCardMatcher(command).get("addCardMainAbbr"));
        else if (chooseAddRemoveCardMatcher(command).get("addCardSide") != null)
            addCardSide(chooseAddRemoveCardMatcher(command).get("addCardSide"));
        else if (chooseAddRemoveCardMatcher(command).get("addCardSideAbbr") != null)
            addCardSide(chooseAddRemoveCardMatcher(command).get("addCardSideAbbr"));
        else if (chooseAddRemoveCardMatcher(command).get("removeCardMain") != null)
            removeCardMain(chooseAddRemoveCardMatcher(command).get("removeCardMain"));
        else if (chooseAddRemoveCardMatcher(command).get("removeCardMainAbbr") != null)
            removeCardMain(chooseAddRemoveCardMatcher(command).get("removeCardMainAbbr"));
        else if (chooseAddRemoveCardMatcher(command).get("removeCardSide") != null)
            removeCardSide(chooseAddRemoveCardMatcher(command).get("removeCardSide"));
        else if (chooseAddRemoveCardMatcher(command).get("removeCardSideAbbr") != null)
            removeCardSide(chooseAddRemoveCardMatcher(command).get("removeCardSideAbbr"));
        else
            return true;
        return false;
    }
    public boolean runShowDeck(String command){
        Matcher showSideDeck = Regex.getCommandMatcherRegexes(command, DeckMenuRegex.showSideDeck);
        Matcher showSideDeckAbbr = Regex.getCommandMatcherRegexes(command, DeckMenuRegex.showSideDeckAbbr);
        if (Regex.getCommandMatcher(command, DeckMenuRegex.showMainDeck).find())
            showDeck(Regex.getCommandMatcher(command, DeckMenuRegex.showMainDeck), true);
        else if (Regex.getCommandMatcher(command, DeckMenuRegex.showMainDeckAbbr).find())
            showDeck(Regex.getCommandMatcher(command, DeckMenuRegex.showMainDeckAbbr), true);
        else if (showSideDeck != null)
            showDeck(showSideDeck, false);
        else if (showSideDeckAbbr != null)
            showDeck(showSideDeckAbbr, false);
        else
            return true;
        return false;
    }
    public void run(String command){
        if (Regex.getCommandMatcher(command, DeckMenuRegex.cardShow).find())
            Card.showCard(Regex.getCommandMatcher(command, DeckMenuRegex.cardShow));
        else if (Regex.getCommandMatcher(command, DeckMenuRegex.createDeck).find())
            createDeck(Regex.getCommandMatcher(command, DeckMenuRegex.createDeck));
        else if (Regex.getCommandMatcher(command, DeckMenuRegex.deleteDeck).find())
            deleteDeck(Regex.getCommandMatcher(command, DeckMenuRegex.deleteDeck));
        else if (Regex.getCommandMatcher(command, DeckMenuRegex.setActiveDeck).find())
            setActiveDeck(Regex.getCommandMatcher(command, DeckMenuRegex.setActiveDeck));
        else if (runAddRemoveCard(command)){
            if (Regex.getCommandMatcher(command, DeckMenuRegex.showAllDecks).find()
                    || Regex.getCommandMatcher(command, DeckMenuRegex.showAllDecksAbbr).find())
                showDecks();
            else if (runShowDeck(command)) {
                if (Regex.getCommandMatcher(command, DeckMenuRegex.showAllCards).find()
                        || Regex.getCommandMatcher(command, DeckMenuRegex.showAllCardsAbbr).find())
                    showCards(getCurrentPlayer().getCards());
                else if (MenuRegex.isNotNavigationCommand(command))
                    Main.outputToUser(MenuResponse.invalidCommand);
            }
        }


    }

}

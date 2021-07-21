package yugioh.model;

import yugioh.Main;
import yugioh.model.cards.Card;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> mainDeck;
    private ArrayList<Card> sideDeck;
    private String name;
    private String ownerUsername;
    public Deck(String name, String ownerUsername){
        setName(name);
        setOwnerUsername(ownerUsername);
        mainDeck = new ArrayList<>();
        sideDeck = new ArrayList<>();
    }
    public Deck(){}
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public boolean isValid() {
        return getMainDeck().size() >= 40;
    }

    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }

    public ArrayList<Card> getSideDeck() {
        return sideDeck;
    }
    public ArrayList<Card> getAllCards(){
        ArrayList<Card> allCards = new ArrayList<>(getMainDeck());
        allCards.addAll(getSideDeck());
        return allCards;
    }
    public int returnCardCountMain(String cardName){
        int count = 0;
        for (Card card : getMainDeck()) {
            if (card.getName().equals(cardName))
                count++;
        }
        return count;
    }
    public int returnCardCountSide(String cardName){
        int count = 0;
        for (Card card : getSideDeck()) {
            if (card.getName().equals(cardName))
                count++;
        }
        return count;
    }
    public int returnCardCountDeck(String cardName){
        int count = 0;
        for (Card card : getAllCards()) {
            if (card.getName().equals(cardName))
                count++;
        }
        return count;
    }

    public void addCardToMainDeck(Card card){
        Player owner = Main.getPlayerByUsername(ownerUsername);
        if (owner == null) return;
        owner.removeFromCards(card);
        mainDeck.add(card);
    }
    public Card getMainDeckCardByName(String cardName){
        for (Card card : getMainDeck()) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }
    public void removeCardFromMainDeck(Card card){
        Player owner = Main.getPlayerByUsername(ownerUsername);
        if (owner == null) return;
        owner.addCard(card);
        mainDeck.remove(card);
    }
    public void addCardToSideDeck(Card card){
        Player owner = Main.getPlayerByUsername(ownerUsername);
        if (owner == null) return;
        owner.removeFromCards(card);
        sideDeck.add(card);
    }
    public Card getSideDeckCardByName(String cardName){
        for (Card card : getSideDeck()) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }
    public void removeCardFromSideDeck(Card card){
        Player owner = Main.getPlayerByUsername(ownerUsername);
        if (owner == null) return;
        owner.addCard(card);
        sideDeck.remove(card);
    }
    public static boolean isDeckValid(Deck deck){
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return name.equals(deck.getName());
    }

}

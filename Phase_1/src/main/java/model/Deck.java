package model;
import model.cards.*;
import model.cards.MonsterCard.MonsterCard;
import model.cards.nonMonsterCard.NonMonsterCard;

import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class Deck {
    private static ArrayList<Deck> decks = new ArrayList<>();
    private ArrayList<Card> mainDeck;
    private ArrayList<Card> sideDeck;
    private String name;
    private Player owner;
    public Deck(String name, Player owner){
        setName(name);
        setOwner(owner);
        mainDeck = new ArrayList<>();
        sideDeck = new ArrayList<>();
        decks.add(this);
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
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
        owner.addCard(card);
        mainDeck.remove(card);
    }
    public void addCardToSideDeck(Card card){
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
        owner.addCard(card);
        sideDeck.remove(card);
    }
    public String isValidToString(boolean isValid){
        if (isValid)
            return "valid";
        return "invalid";
    }

    public String toStringForShowDecks(){
        return getName() + ": main deck " + getMainDeck().size() + ", side deck " +
                getSideDeck().size() + ", " + isValidToString(isValid()) + "\n";
    }
    public String showDeckCards(ArrayList<Card> monsters, ArrayList<Card> nonMonsters, boolean isMain){
        Comparator<Card> cardComparator = Comparator.comparing(Card::getName);
        List<Card> sortedMonsters = monsters.stream().sorted(cardComparator).collect(Collectors.toList());
        List<Card> sortedNonMonsters = nonMonsters.stream().sorted(cardComparator).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        if (isMain)
            stringBuilder.append("Deck: " + getName() + "\nMain deck:\nMonsters:\n");
        else
            stringBuilder.append("Deck: " + getName() + "\nSide deck:\nMonsters:\n");
        for (Card sortedMonster : sortedMonsters) {
            stringBuilder.append(sortedMonster.getName() + ": " + sortedMonster.getDetails() + "\n");
        }
        stringBuilder.append("Spell and Traps:\n");
        for (Card sortedNonMonster : sortedNonMonsters) {
            stringBuilder.append(sortedNonMonster.getName() + ": " + sortedNonMonster.getDetails() + "\n");
        }
        return stringBuilder.toString();

    }
    public String toStringMainDeck(){
        ArrayList<Card> monsters = new ArrayList<>();
        ArrayList<Card> nonMonsters = new ArrayList<>();
        for (Card card : getMainDeck()) {
            if (Initializer.monsterCardToBuild(card.getName()) != null)
                monsters.add(card);
            else if (Initializer.spellTrapCardToBuild(card.getName()) != null)
                nonMonsters.add(card);
        }
        return showDeckCards(monsters, nonMonsters, true);

    }
    public String toStringSideDeck(){
        ArrayList<Card> monsters = new ArrayList<>();
        ArrayList<Card> nonMonsters = new ArrayList<>();
        for (Card card : getSideDeck()) {
            if (Initializer.monsterCardToBuild(card.getName()) != null)
                monsters.add(card);
            else if (Initializer.spellTrapCardToBuild(card.getName()) != null)
                nonMonsters.add(card);
        }
        return showDeckCards(monsters, nonMonsters, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deck deck = (Deck) o;
        return name.equals(deck.name) && Objects.equals(owner, deck.owner);
    }

}

package model;
import model.cards.*;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class Deck {
    private ArrayList<Card> cards;
    private String name;
    public Deck(String name){
        setName(name);
        cards = new ArrayList<>();
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void addCardToDeck(Card card){
        cards.add(card);
    }
    public void removeCardFromDeck(Card card){
        cards.remove(card);
    }
    public static boolean isDeckValid(Deck deck){
        return true;
    }
    public void showCards(Matcher matcher){

    }

}

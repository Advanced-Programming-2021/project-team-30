package model;

import model.cards.Card;

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
    public void addCardToDeck(Card card){

    }
    public void removeCardFromDeck(Card card){

    }
    public static boolean isDeckValid(Deck deck){
        return true;
    }
    public void showCards(Matcher matcher){

    }

}

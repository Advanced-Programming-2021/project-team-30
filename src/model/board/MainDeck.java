package model.board;

import model.cards.Card;

import java.util.ArrayList;
import java.util.Random;

public class MainDeck{
    private ArrayList<Card> cards = new ArrayList<Card>();
    final Board board;
    final Random rand = new Random();

    public MainDeck(Board board, ArrayList<Card> cards){
        this.board = board;
        this.cards.addAll(cards);
    }

    public void reset(){
        cards.clear();
    }

    public int total(){
        return cards.size();
    }

    public Card getCard(){ return cards.get(rand.nextInt(cards.size())); }

    public Card getCard(int location){ return cards.get(location); }

    public void addCard(Card card){ cards.add(card); }

    public void removeCard(Card card){ cards.remove(card); }

    public void removeCards(ArrayList<Card> cards){
        for(Card card: cards)removeCard(card);
    }

    public void removeCard(int location){ cards.remove(location); }
}

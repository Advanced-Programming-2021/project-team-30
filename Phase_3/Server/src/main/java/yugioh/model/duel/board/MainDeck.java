package yugioh.model.duel.board;

import yugioh.model.cards.Card;
import java.util.ArrayList;
import java.util.Random;


public class MainDeck{
    final ArrayList<Card> finalCards;
    final Random rand = new Random();
    private ArrayList<Card> cards = new ArrayList<>();

    public MainDeck(ArrayList<Card> cards){
        this.cards.addAll(cards);
        this.finalCards = cards;
    }

    public boolean doesCardWithNameExist(String name){
        for(Card card : cards)if(card.getName().equals(name))return true;
        return false;
    }

    public void reset(){
        cards.clear();
        cards = finalCards;
    }

    public int total(){
        return cards.size();
    }

    public Card getCard(){ return cards.get(rand.nextInt(cards.size())); }

    public Card getCard(int location){ return cards.get(location); }

    public void addCard(Card card){ cards.add(card); }

    public void removeCard(int location){ cards.remove(location); }

    public boolean isThereCardOnLocation(int location) {
        return cards.size() > location;
    }
}

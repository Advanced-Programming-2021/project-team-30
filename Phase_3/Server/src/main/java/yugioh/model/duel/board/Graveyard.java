package yugioh.model.duel.board;

import yugioh.model.cards.Card;
import java.util.ArrayList;


public class Graveyard{
    private final ArrayList<Card> cards = new ArrayList<>();

    public void reset(){ cards.clear(); }

    public int total(){
        return cards.size();
    }

    public void removeCard(int location){
        cards.remove(location);
    }

    public void removeCard(String cardName){
        for(Card card: cards)
            if(card.getName().equals(cardName)) {
                cards.remove(card);
                return;
            }
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public Card getCard(int location){
        return cards.get(location);
    }

    public Card getCard(String cardName){
        for(Card card: cards)
            if(card.getName().equals(cardName))
                return card;
        return null;
    }

    public ArrayList<Card> getAll(){
        return this.cards;
    }

    public boolean isThereCardOnLocation(int location) {
        return cards.size() > location;
    }
}

package model.board;

import model.Ground;
import model.cards.Card;
import model.cards.MonsterCard.MonsterCard;
import model.cards.nonMonsterCard.Spell.Spell;
import model.cards.nonMonsterCard.Trap.Trap;
import model.response.DuelMenuResponse;
import view.Main;

import java.util.ArrayList;

public class Graveyard{
    private final ArrayList<Card> cards = new ArrayList<>();
    final Board board;

    public Graveyard(Board board){ this.board = board; }

    public void reset(){ cards.clear(); }

    public int total(){
        return cards.size();
    }

    public void removeCard(int location){
        cards.remove(location);
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public Card findCard(Card card){
        int location = cards.indexOf(card);
        if(location == -1){
            Main.outputToUser(DuelMenuResponse.noCardFound);
            return null;
        }
        else
            return cards.get(location);
    }

    public Card getCard(int location){
        return cards.get(location);
    }

    public ArrayList<Card> getAll(){
        return this.cards;
    }

    public boolean isThereCardOnLocation(int location) {
        return cards.size() > location;
    }
}

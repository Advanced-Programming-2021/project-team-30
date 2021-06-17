package model.board;

import model.cards.Card;
import model.cards.MonsterCard.MonsterCard;
import model.cards.nonMonsterCard.Spell.Spell;
import model.cards.nonMonsterCard.Trap.Trap;

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

    public boolean doesCardWithNameExist(String name){
        for(Card card : cards)if(card.getName().equals(name))return true;
        return false;
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

    public boolean isThereCardOnLocation(int location) {
        return cards.size() > location;
    }

    public void specialSummon(int location) {
        Card card = getCard(location);
        removeCard(location);
        if(card instanceof MonsterCard)
            board.monsterPlayGround.addCard((MonsterCard) card, "OO");
        else if(card instanceof Spell || card instanceof Trap)
            board.spellTrapPlayGround.addCard(card, "O");
    }
}

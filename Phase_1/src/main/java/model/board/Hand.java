package model.board;

import model.cards.Card;
import model.cards.MonsterCard.MonsterCard;
import model.cards.nonMonsterCard.Spell.Spell;
import model.cards.nonMonsterCard.Trap.Trap;

import java.util.ArrayList;

public class Hand{
    private final ArrayList<Card> cards = new ArrayList<>();
    private final ArrayList<Boolean> isRequirementsDone = new ArrayList<>();
    private final Board board;

    public Hand(Board board){
        this.board = board;
        reset();
    }

    public void reset(){
        cards.clear();
        isRequirementsDone.clear();
    }

    public int total(){
        return cards.size();
    }

    public void removeCard(int location){
        cards.remove(location);
        isRequirementsDone.remove(location);
    }

    public Card getCard(int location){
        return cards.get(location);
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public ArrayList<MonsterCard> getAllMonsterCards(){
        ArrayList<MonsterCard> answer = new ArrayList<>();
        for(Card card: cards)if(card instanceof MonsterCard) answer.add((MonsterCard) card);
        return answer;
    }

    public boolean doesCardWithNameExist(String name){
        for(Card card : cards)if(card.getName().equals(name)) return true;
        return false;
    }

    public boolean getRequirementsStatus(int location){
        return isRequirementsDone.get(location);
    }

    public void setRequirementsStatus(int location, Boolean setter){
        isRequirementsDone.set(location, setter);
    }
}

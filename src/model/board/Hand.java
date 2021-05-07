package model.board;

import model.cards.Card;

import java.util.ArrayList;

public class Hand{
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<Boolean> isRequirementsDone = new ArrayList<>();
    private Board board;

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
        isRequirementsDone.add(board.checkRequirements(card));
    }

    public boolean getRequirementsStatus(int location){
        return isRequirementsDone.get(location);
    }

    public void setRequirementsStatus(int location, Boolean setter){
        isRequirementsDone.set(location, setter);
    }
}

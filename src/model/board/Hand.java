package model.board;

import model.cards.Card;

import java.util.ArrayList;

public class Hand extends Board{
    private ArrayList<Card> cards = new ArrayList<>();
    private ArrayList<boolean> isRequirementsDoneStatus = new ArrayList<>();

    @Override
    public void init() {
        ;
    }

    @Override
    public void show() {

    }

    @Override
    public int total(){
        return cards.size();
    }

    @Override
    public void removeCard(int location){
        cards.remove(location);
    }

    public void summon(int location, int target){

    }

    public void set(int location, int target){

    }

    public Card getCard(int location){
        return cards[location];
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public void removeCard(int location){
        cards.remove(location);
    }

    public boolean getRequirementsStatus(int location){
        return isRequirementsDoneStatus[location];
    }

    public boolean setRequirementsStatus(int location, boolean setter){
        isRequirementsDoneStatus[location] = setter;
    }
}

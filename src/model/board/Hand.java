package model.board;

import model.cards.Card;

import java.util.ArrayList;

public class Hand extends Board{
    private ArrayList<Card> cards = new ArrayList<>();

    @Override
    public void init() {
        //
    }

    @Override
    public void show() {

    }

    public void summon(int location, int target){

    }

    public void set(int location, int target){

    }

    public Card getCard(int location){

    }

    public void addCard(Card card){

    }

    public void removeCard(){
        cards.remove(selectedCardLocation);
    }

}

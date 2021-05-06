package model.board;

import model.cards.Card;

import java.util.ArrayList;

public class SpellTrapPlayground extends Board{
    Card[] cards = new String[5];

    @Override
    public void init() {
        for(int i = 0; i < 5; i++)this.cards[i] = null;
    }

    @Override

    public Card search(int location){
        return this.cards[location];
    }

    public void show() {

    }
    public void activate(int location){

    }
    public void remove(int location){

    }
    public int getNumber(){

    }
}

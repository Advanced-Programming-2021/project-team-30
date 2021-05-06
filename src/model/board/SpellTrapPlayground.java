package model.board;

import model.cards.Card;

import java.util.ArrayList;

public class SpellTrapPlayground extends Board{
    private Board board;
    private Card[] cards = new Card[5];
    private String[] position = new String[5];

    @Override
    public void init() {
        for(int i = 0; i < 5; i++){
            cards[i] = null;
            position[i] = null;
        }
    }

    @Override
    public Card search(int location){
        return this.cards[location];
    }

    @Override
    public int total(){
        int counter = 0;
        for(int i = 0; i < 5; i++)
            if(cards[i] != null) counter++;
        return counter;
    }

    public SpellTrapPlayground(Board board){
        this.board = board;
        init();
    }

    public void show() {

    }
    public void activate(int location){

    }
    public void remove(int location){

    }
    public int getNumber(){

    }

    public String getPosition(int location){
        return position[location];
    }
}

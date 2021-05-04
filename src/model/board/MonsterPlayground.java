package model.board;
import model.cards.MonsterCard.*;

import model.cards.Card;

import java.util.ArrayList;

public class MonsterPlayground extends Board{

    private Card[] cards = new Card[5];

    @Override
    public void init() {
        for(int i = 0; i < 5; i++) this.cards[i] = null;
    }


    @Override
    public void show() {
        // wait
    }


    public Card search(int location){
        return this.cards[location];
    }


    public void flipSummon(int location){

    }


    public void setStatus(int location){

    }
    public void attack(int myLocation, int enemyLocation){

    }
    public int getNumber(){

    }
}

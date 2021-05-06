package model.board;
import model.cards.MonsterCard.*;

import model.cards.Card;

import java.util.ArrayList;

public class MonsterPlayground extends Board{

    private Card[] cards = new Card[5];
    private String[] situation = new String[5];

    @Override
    public void init() {
        for(int i = 0; i < 5; i++){
            this.cards[i] = null;
            this.situation[i] = "E";
        }
    }

    @Override
    public void show() {
        // wait
    }

    @Override

    public void set(){
        add(null, "DH");
    }

    public Card search(int location){
        return this.cards[location];
    }

    public int total(){
        
        for(int i = 4; i >= 0; i--)
            if(this.cards[i] != null) return i + 1;

        return 0;
    }

    public void add(Card card, String situation){
        if(card == null) card = selectCard;
        for(int i = 0; i < 5; i++)if(this.cards[i] == null){
            this.cards[i] = card;
            this.situation[i] = situation;
        }
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

package model.board;
import model.cards.MonsterCard.*;

import model.cards.Card;

import java.util.ArrayList;

import model.baords.Board;

public class MonsterPlayground extends Board{

    private Board board;
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

    @Override
    public int total(){
        int counter = 0;
        for(int i = 0; i < 5; i++)
            if(cards[i] != null) counter++;
        return counter;
    }

    public MonsterPlayground(Board board){
        this.board = board;
        init();
    }

    public Card search(int location){
        return this.cards[location];
    }

    public void add(Card card, String situation){
        if(card == null) card = selectCard;
        for(int i = 0; i < 5; i++)if(this.cards[i] == null){
            this.cards[i] = card;
            this.situation[i] = situation;
        }
    }

    public boolean flipSummon(){
        if(duel.askPositionChange(selectedCardLocation) || situation[selectedCardLocation] != "DH"){
            //message: you can't flip-summon this card
            return 0;
        }

        situation[selectedCardLocation] = "OO";
        //message: flip-summoned successfully
        return true;
    }


    public boolean setPosition(String newPosition){
        if(newPosition == cards[selectedCardLocation]){
            //message: this card is already in the wanted position
            return false;
        }
        //message: monster card position changed successfully
        situation[selectedCardLocation] = newPosition;
        return true;
    }

    public String getPosition(int location){
        return situation[location];
    }

    public int getNumber(){

    }
}

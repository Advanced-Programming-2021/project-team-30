package model.board;
import model.cards.MonsterCard.*;

import model.cards.Card;

import java.util.ArrayList;

import model.baords.Board;

public class MonsterPlayground extends Board{

    private Board board;
    private Card[] cards = new Card[5];
    private String[] position = new String[5];

    @Override
    public void init() {
        for(int i = 0; i < 5; i++){
            cards[i] = null;
            position[i] = "E";
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

    @Override
    public void removeCard(int location){
        cards[location] = null;
        position[location] = "E";
    }

    public MonsterPlayground(Board board){
        this.board = board;
        init();
    }

    public Card search(int location){
        return cards[location];
    }

    public void add(Card card, String position){
        if(card == null) card = selectCard;
        for(int i = 0; i < 5; i++)if(cards[i] == null){
            cards[i] = card;
            position[i] = position;
        }
    }

    public boolean flipSummon(){
        if(duel.askPositionChange(selectedCardLocation) || position[selectedCardLocation] != "DH"){
            //message: you can't flip-summon this card
            return 0;
        }

        position[selectedCardLocation] = "OO";
        //message: flip-summoned successfully
        return true;
    }


    public boolean setPosition(String newPosition){
        if(newPosition == cards[selectedCardLocation]){
            //message: this card is already in the wanted position
            return false;
        }
        //message: monster card position changed successfully
        position[selectedCardLocation] = newPosition;
        return true;
    }

    public String getPosition(int location){
        return position[location];
    }

    public int getNumber(){

    }
}

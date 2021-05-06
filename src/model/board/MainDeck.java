package model.board;

import model.cards.Card;

import java.util.ArrayList;

public class MainDeck extends Board{
    private ArrayList<Card> cards = new ArrayList<>();

    @Override
    public void init() {

    }

    @Override
    public void show() {

    }

    @Override
    public int total(){
        return cards.size();
    }

    public void removeCard(int location){
        cards.remove(location);
    }

    public void shuffle(){

    }
    public void draw(){

    }
    public void addCard(Card card){

    }
    public void removeCard(Card card){

    }
    public int getNumber(){

    }
}

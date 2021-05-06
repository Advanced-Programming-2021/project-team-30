package model.board;

import model.cards.Card;

import java.util.ArrayList;

public class Graveyard extends Board{
    private ArrayList<Card> cards = new ArrayList<>();

    @Override
    public void init() {
        ;
    }

    @Override
    public void show() {
        for(Card card: cards){
            this.duel.showCard(card);
        }
    }

    public void addCard(Card card){
        this.cards.add(card);
    }

    public Card findCard(Card card){
        int index = cards.indexOf(card);
        if(index == -1){
            //message: no such card in the graveyard!
            return null;
        }
        else
            return cards[index];
    }

    public void removeCard(Card card){
        int index = cards.indexOf(card);
        if(index == -1){
            //message: no such card in the graveyard!
            return;
        }

        cards.remove(index);
    }

    public int getNumber(){
        return 1 ;
    }

}

package model.board;

import model.cards.Card;

import java.util.ArrayList;

public class SpellTrapPlayground extends Board{
    private Board board;
    private Card[] cards = new Card[5];
    private String[] position = new String[5];
    private boolean[] isRequirementsDoneStatus = new boolean[5];

    @Override
    public void init() {
        for(int i = 0; i < 5; i++){
            cards[i] = null;
            position[i] = "E";
        }
    }

    @Override
    public Card search(int location){
        return cards[location];
    }

    @Override
    public int total(){
        int counter = 0;
        for(int i = 0; i < 5; i++)
            if(cards[i] != null) counter++;
        return counter;
    }


    @Override
    public void remove(int location){
        cards[location] = null;
        position[location] = "E";
    }

    public SpellTrapPlayground(Board board){
        this.board = board;
        init();
    }

    public void addCard(Card card, String position){
        for(int i = 0; i < 5; i++)if(cards[i] == null){
            cards[i] = card;
            this.position[i] = position;
        }
    }

    public void show() {

    }
    public void activate(int location){

    }
    public int getNumber(){

    }

    public String getPosition(int location){
        return position[location];
    }

    public boolean setRequirementsStatus(int location, boolean setter){
        isRequirementsDoneStatus[location] = setter;
    }
    
    public boolean getRequirementsStatus(int location){
        return isRequirementsDoneStatus[location];
    }
}

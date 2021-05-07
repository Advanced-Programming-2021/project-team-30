package model.board;
import model.cards.MonsterCard.*;

import model.cards.Card;

import java.util.ArrayList;

import model.board.Board;

public class MonsterPlayground{

    final Board board;
    private Card[] cards = new Card[5];
    private String[] position = new String[5];

    public void reset() {
        for(int i = 0; i < 5; i++){
            cards[i] = null;
            position[i] = "E";
        }
    }

    public MonsterPlayground(Board board){
        this.board = board;
        reset();
    }

    public void show() {
        // wait
    }

    public void set(){
        addCard(board.getSelectedCard(), "DH");
    }

    public int total(){
        int counter = 0;
        for(int i = 0; i < 5; i++)
            if(cards[i] != null) counter++;
        return counter;
    }

    public void removeCard(int location){
        board.undoEffect(cards[location]);
        cards[location] = null;
        position[location] = "E";
    }

    public Card search(int location){
        return cards[location];
    }

    public void addCard(Card card, String position){
        if(card == null) card = board.getSelectedCard();
        for(int i = 0; i < 5; i++)if(cards[i] == null) {
            cards[i] = card;
            this.position[i] = position;
            board.doEffect(card);
            return;
        }
    }

    public boolean flipSummon(){
        if(board.askPositionChange(board.getSelectedCardLocation(), 0) || !position[board.getSelectedCardLocation()].equals("DH")){
            board.setMessage("you can't flip-summon this card");
            return false;
        }

        position[board.getSelectedCardLocation()] = "OO";
        board.setMessage("flip-summoned successfully");
        return true;
    }

    public boolean setPosition(String newPosition){
        if(newPosition.equals(position[board.getSelectedCardLocation()])){
            board.setMessage("this card is already in the wanted position");
            return false;
        }
        board.setMessage("monster card position changed successfully");
        position[board.getSelectedCardLocation()] = newPosition;
        return true;
    }

    public String getPosition(int location){
        return position[location];
    }

    public boolean getRequirementsStatus(int location){
        return board.checkRequirements(cards[location]);
    }
}

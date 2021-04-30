package model.board;

import model.Card;

public class FieldZone extends Board{
    private Card field;
    @Override
    public void init() {

    }

    @Override
    public void show() {

    }
    public void change(Card card){

    }
    public void remove(Card card){

    }

    public void setField(Card field) {
        this.field = field;
    }

    public Card getField() {
        return field;
    }
}

package model.board;

import model.cards.Card;

public class FieldZone extends Board{
    private Card field;
    @Override
    public void init() {
        this.field = null;
    }

    @Override
    public void show() {
        this.duel.showCard(this.field);
    }

    public void getCard(){
        return this.field;
    }

    public void change(Card card){
        if(this.field != null)
            this.duel.undoEffect(this.field);
        this.duel.doEffect(card);
        this.field = card;    
    }

    public void remove(){
        if(this.field == null){
            //message: field is already empty
            return;
        }
        this.field = null;
        this.duel.undoEffect(card);
    }

    public void setField(Card field) {
        
        this.field = field;
    }
}

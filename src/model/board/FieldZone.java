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

    @Override
    public int total(){
        if(getCard() == null) return 0;
        return 1;
    }

    @Override
    public void removeCard(){
        if(field == null){
            //message: field is already empty
            return;
        }
        field = null;
        duel.undoEffect(card);
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

    public void setField(Card field) {
        
        this.field = field;
    }
}

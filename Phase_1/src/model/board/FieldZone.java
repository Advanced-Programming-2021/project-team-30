package model.board;
import model.cards.Card;

public class FieldZone{
    private Card field;
    final Board board;

    public FieldZone(Board board){
        this.board = board;
        reset();
    }

    public void setField(Card field) {
        this.field = field;
    }

    public void reset() {
        this.field = null;
    }

    public int total(){
        if(getCard() == null) return 0;
        return 1;
    }

    public void removeCard(){
        if(field == null){
            //message: field is already empty
            return;
        }
        board.undoEffect(field);
        field = null;
    }

    public Card getCard(){
        return field;
    }

    public void change(Card card){
        if(this.field != null)
            board.undoEffect(field);
        board.doEffect(field);
        this.field = card;    
    }

}

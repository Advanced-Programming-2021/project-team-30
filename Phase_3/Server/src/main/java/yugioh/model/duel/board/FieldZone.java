package yugioh.model.duel.board;
import yugioh.model.cards.Card;


public class FieldZone{
    private Card field;

    public FieldZone(){
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
        //field.undoEffect();
        field = null;
    }

    public Card getCard(){
        return field;
    }

    public void change(Card card){
//        if(field != null)
//            field.undoEffect();
        field = card;
//        field.doEffect(field.getEffect());
    }

    public boolean isFull() {
        return field != null;
    }
}

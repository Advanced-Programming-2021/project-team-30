package model.cards.nonMonsterCard;

import model.cards.Card;

public class NonMonsterCard extends Card {

    protected String type ;
    protected int speed ;

    public NonMonsterCard(String name, int price, String details) {
        super(name, price, details);
    }

    public void summon() {
        // ??
    }
    public void set() {
        // ??
    }
    public String getType(){
        return this.type;
    }
}

package yugioh.model.cards.nonMonsterCard.Trap;

import yugioh.model.cards.nonMonsterCard.NonMonsterCard;

public class Trap extends NonMonsterCard {
    private String type ;
    private int speed ;

    public Trap(String name, int price, String details) {
        super(name, price, details);
    }

    public void active() {
        // ??
    }
}

package yugioh.model.cards.nonMonsterCard.Trap;

import yugioh.model.cards.Icon;
import yugioh.model.cards.nonMonsterCard.NonMonsterCard;

public class Trap extends NonMonsterCard {
    private String type ;
    private int speed ;

    public Trap(String name, boolean isSpell, Icon icon, String details, boolean isLimited, int price) {
        super(name, isSpell, icon, details, isLimited, price);
    }


    public void active() {
        // ??
    }
}

package yugioh.model.cards.nonMonsterCard.Trap;

import yugioh.model.cards.Icon;

public class NormalTrap extends Trap{

    public NormalTrap(String name, boolean isSpell, Icon icon, String details, boolean isLimited, int price) {
        super(name, isSpell, icon, details, isLimited, price);
    }
}

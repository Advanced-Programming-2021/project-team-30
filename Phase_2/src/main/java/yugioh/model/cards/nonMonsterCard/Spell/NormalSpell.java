package yugioh.model.cards.nonMonsterCard.Spell;

import yugioh.model.cards.Icon;

public class NormalSpell extends Spell {

    public NormalSpell(String name, boolean isSpell, Icon icon, String details, boolean isLimited, int price) {
        super(name, isSpell, icon, details, isLimited, price);
    }

    public void normalSummon() {
        // ?
    }
}

package model.cards.nonMonsterCard.Spell;

import model.effect.Effect;

public class FieldSpell extends Spell {
    public FieldSpell(String name, int price, String details) {
        super(name, price, details);
    }

    public void normalSummon() {
        // ?
    }

    @Override
    public Effect getEffect(){
        return effects.get(0);
    }
}

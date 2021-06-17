package model.cards.nonMonsterCard.Spell;

import model.effect.Effect;

public class FieldSpell extends Spell {
    public void normalSummon() {
        // ?
    }

    @Override
    public Effect getEffect(){
        return effects.get(0);
    }
}

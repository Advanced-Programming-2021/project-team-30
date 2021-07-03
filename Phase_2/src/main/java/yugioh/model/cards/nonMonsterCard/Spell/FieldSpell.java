package yugioh.model.cards.nonMonsterCard.Spell;


import yugioh.model.cards.Icon;

public class FieldSpell extends Spell {
    public FieldSpell(String name, boolean isSpell, Icon icon, String details, boolean isLimited, int price) {
        super(name, isSpell, icon, details, isLimited, price);
    }

//    @Override
//    public Effect getEffect(){
//        return effects.get(0);
//    }
}

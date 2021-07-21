package yugioh.model.cards.nonMonsterCard.Spell;

import yugioh.model.cards.Icon;
import yugioh.model.cards.Type;
import yugioh.model.cards.nonMonsterCard.NonMonsterCard;

public class Spell extends NonMonsterCard {

    public Spell(String name, boolean isSpell, Icon icon, String details, boolean isLimited, int price) {
        super(name, isSpell, icon, details, isLimited, price);
    }


    public Type method(Type type){
        return type ;
    }
}

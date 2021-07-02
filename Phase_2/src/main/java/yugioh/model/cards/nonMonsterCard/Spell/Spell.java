package yugioh.model.cards.nonMonsterCard.Spell;

import yugioh.model.cards.Type;
import yugioh.model.cards.nonMonsterCard.NonMonsterCard;

public class Spell extends NonMonsterCard {
    private String icon ;

    public Spell(String name, int price, String details) {
        super(name, price, details);
    }

    public Type method(Type type){
        return type ;
    }
}

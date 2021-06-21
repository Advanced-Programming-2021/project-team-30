package model.cards.nonMonsterCard.Spell;

import model.cards.Type;
import model.cards.nonMonsterCard.NonMonsterCard;

public class Spell extends NonMonsterCard {
    private String icon ;

    public Spell(String name, int price, String details) {
        super(name, price, details);
    }

    public Type method(Type type){
        return type ;
    }
}

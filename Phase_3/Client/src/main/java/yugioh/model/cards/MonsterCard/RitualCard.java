package yugioh.model.cards.MonsterCard;

import yugioh.model.cards.Attribute;
import yugioh.model.cards.Type;

import java.util.ArrayList;

public class RitualCard extends MonsterCard {
    public RitualCard(String name, int price, String details, boolean hasEffect , int attackDamage , int defenseDamage , int level , ArrayList<Type> types , Attribute attribute) {
        super(name, price, details, hasEffect, attackDamage, defenseDamage, level, types, attribute);
    }
}

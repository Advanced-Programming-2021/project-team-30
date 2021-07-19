package model.cards.MonsterCard;

import model.cards.Attribute;
import model.cards.Type;

import java.util.ArrayList;

public class EffectCard extends MonsterCard{
    public EffectCard(String name, int price, String details, int attackDamage , int defenseDamage , int level , ArrayList<Type> types , Attribute attribute) {
        super(name, price, details, attackDamage, defenseDamage, level, types, attribute);
    }
}

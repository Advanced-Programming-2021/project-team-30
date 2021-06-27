package model.cards.MonsterCard;

import model.cards.Attribute;
import model.cards.Type;
import java.util.ArrayList;


public class NormalCard extends MonsterCard {
    public NormalCard(String name, int price, String details, int health , int attackDamage , int defenseDamage , int level , ArrayList<Type> types , Attribute attribute) {
        super(name, price, details, health, attackDamage, defenseDamage, level, types, attribute);
    }
}

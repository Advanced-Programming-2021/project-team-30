package yugioh.model.cards.MonsterCard;

import yugioh.model.cards.Attribute;
import yugioh.model.cards.Card;
import yugioh.model.cards.Type;

import java.util.ArrayList;

public class MonsterCard extends Card {
    protected int health , attackDamage , defenseDamage , level, tributes = 0;
    protected ArrayList<Type> types;
    protected Attribute attribute ;

    public MonsterCard(String name, int price, String details, int health , int attackDamage , int defenseDamage ,int level ,ArrayList<Type> types , Attribute attribute) {
        super(name, price, details);
        this.health = health;
        this.attackDamage = attackDamage;
        this.defenseDamage = defenseDamage;
        this.level = level;
        this.types = types;
        this.attribute = attribute;
    }

    public int showHealth() {
        return this.health ;
    }

    public void changeHealth(int change) { this.health = change; }

    public int getAttackDamage() {
        return attackDamage;
    }

    public int getDefenseDamage() {
        return defenseDamage;
    }

    public void addAttackDamage(int damage){ this.attackDamage += damage; }

    public void addDefenseDamage(int damage){ this.defenseDamage += damage; }

    public int getLevel() {
        return level;
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public int getTributes(){ return tributes; }

    public boolean checkTributes() {
        if(level >= 7) return tributes == 2;
        else if(level >= 5) return tributes == 1;
        else return true;
    }

    public boolean isItPossibleToTribute(int totalCards){
        if(level >= 7) return totalCards >= 2;
        else if(level >= 5) return totalCards >= 1;
        else return true;
    }

    public void setAttackDamage(int damage) {
        this.attackDamage = damage;
    }
}
package model.cards.MonsterCard;

import model.cards.Attribute;
import model.cards.Card;
import model.cards.Type;
import java.util.ArrayList;

public class MonsterCard extends Card {
    protected int health , attackDamage , defenseDamage , level, tributes = 0;
    protected ArrayList<Type> types = new ArrayList<Type>() ;
    protected Attribute attribute ;

    public MonsterCard(String name, int price, String details) {
        super(name, price, details);
    }

    public void MonsterCard(int heath , int attackDamage , int defenseDamage ,int level ,ArrayList<Type> types , Attribute attribute ,String details ) {
    }
    public int showHealth() {
        return this.health ;
    }
    public void changeHealth(int change) { this.health = change; }
    public void activeCard() {
        /// ??
    }
    public int getAttackDamage() {
        return attackDamage;
    }

    public int getDefenseDamage() {
        return defenseDamage;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public Attribute getAttribute() {
        return attribute;
    }
    public void summon() {
        // ??
    }
    public void set() {
        // ??
    }

    public void flip() {

    }

    public void addAttackDamage(int damage){ this.attackDamage += damage; }

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
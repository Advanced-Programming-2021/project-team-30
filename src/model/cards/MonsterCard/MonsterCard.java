package model.cards.MonsterCard;
import model.cards.Attribute;
import model.cards.Card;
import model.cards.Type;
import java.util.ArrayList;

public class MonsterCard extends Card {
    protected int heath , attackDamage , defenseDamage , level ;
    protected ArrayList<Type> types = new ArrayList<Type>() ;
    protected Attribute attribute ;
    public void MonsterCard(int heath , int attackDamage , int defenseDamage ,int level ,ArrayList<Type> types , Attribute attribute ,String details ) {
    }
    public int showHealth() {
        return this.heath ;
    }
    public void changeHealth(int change) {
        this.heath = change ;
    }
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

    }

    public void flip() {

    }
 }
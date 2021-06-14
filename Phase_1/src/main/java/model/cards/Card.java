package model.cards;
import model.Prototype;

import java.lang.String;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import model.event.*;
import model.requirements.Requirements;
import model.requirements.*;
import model.effect.*;

public class Card implements Prototype {
    protected final ArrayList<Events> triggers = new ArrayList<>();
    protected final ArrayList<Requirement> requirements = new ArrayList<>();
    protected final ArrayList<Effect> actions = new ArrayList<>();

    protected boolean isInGraveyard = false ;
    protected Type cardType ;
    protected Attribute status ;
    protected int price , speed , cardNumber , quantity ;
    protected String name , details ;
    public Card(String name , int price ,String details) {
        setDetails(details);
        setName(name);
        setPrice(price);
    }

    @Override
    public Prototype getClone(){
        return new Card(name, price, details);
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setDetails(String details) {
        this.details = details;
    }

    private void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getDetails() {
        return details;
    }
    public String getName() {
        return name ;
    }
    public boolean isInGraveyard() {
        return isInGraveyard ;
    }
    public Attribute getStatus() {
        return status;
    }
    public int getQuantity() {
        return quantity;
    }
    public Type getCardType() {
        return cardType;
    }
    public void addQuantity() {
        quantity++ ;
    }

    public void doEffect(){
        for(int i = 0; i < triggers.size(); i++)
            if(checkTrigger(triggers.get(i))){
                actions.get(i).doEffect();
        }
    }

    public boolean checkEffects(){
        for(int i = 0; i < triggers.size(); i++)if(checkTrigger(triggers.get(i))){
            if(requirements.get(i).check()){
                return true;
            }
        }
        return false;
    }

    public boolean checkTrigger(Events trigger){
        return switch (trigger) {
            case OnDeath -> OnDeath.isCalled;
            case OnEnemyBattlePhase -> OnEnemyBattlePhase.isCalled;
            case OnFlip -> OnFlip.isCalled;
            case OnGettingAttacked -> OnGettingAttacked.getInstance().isCalled;
            case OnSpellActivation -> OnSpellActivation.isCalled;
            case OnStandByPhase -> OnStandByPhase.isCalled;
            case OnSummon -> OnSummon.isCalled;
        };
    }

    public static void showCard(Matcher matcher){
        if (matcher.find()){
            String cardName = matcher.group("cardName");
        }
    }
}

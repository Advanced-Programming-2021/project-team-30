package model.cards;

import model.Ground;
import model.Prototype;
import java.lang.String;
import java.util.ArrayList;
import java.util.regex.Matcher;
import model.effect.Effect;
import model.effect.event.*;

public class Card implements Prototype {

    protected ArrayList<Effect> effects = new ArrayList<>();
    protected boolean isInGraveyard = false, isBlocked = false;
    protected Type cardType;
    protected Attribute status;
    protected int price, speed, cardNumber, quantity ;
    protected String name, description;
    protected Ground ground;
    protected int location;
    public Card(String name , int price ,String description) {
        setDetails(description);
        setName(name);
        setPrice(price);
    }

    @Override
    public Prototype getClone(){
        return new Card(name, price, description);
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setDetails(String description) {
        this.description = description;
    }

    private void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getDetails() {
        return description;
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

    public void doEffect(Effect effect){
        if(effect != null){
            effect.doEffect();
            return;
        }
    }

    public Effect getEffect(){
        ArrayList<Effect> triggeredEffects = new ArrayList<>();
        for(Event event: Event.events)if(event.isCalled)
            triggeredEffects = event.getRegisteredEffects();

        for(Effect effect: triggeredEffects)
            if(findEffect(effect))
                return effect;
        return null;
    }

    public boolean findEffect(Effect effect){
        for(Effect myEffect: effects)
            if(effect == myEffect)
                return true;
        return false;
    }

    public boolean checkEffects(){
        Effect effect = getEffect();
        return effect != null;
    }

    public void addEffect(Effect effect){
        effects.add(effect);
    }

    public static void showCard(Matcher matcher){
        if (matcher.find()){
            String cardName = matcher.group("cardName");
        }
    }

    public void undoEffect() {
        for(Effect effect: effects)
            effect.undoEffect();
    }

    public void setCardBlockedStatus(boolean status) {
        isBlocked = status;
    }

    public void setLocation(int location) {
        this.location = location;
        for(Effect effect: effects)
            effect.getRequirement().setMyLocation(location);
    }

    public void setGround(Ground ground){
        this.ground = ground;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return price == card.price && name.equals(card.name) && description.equals(card.description);
    }
}

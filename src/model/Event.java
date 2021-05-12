package model;

import model.cards.Card;

import java.util.ArrayList;
import java.util.Objects;
import model.Duel;

public class Event {
    private static ArrayList<Event> events;
    private String name;
    private static ArrayList<Card> cards;
    final static Duel duel = Duel.getRecentDuel();
    final Card card;
    final boolean[] triggers = new boolean[14];
    final int[] requirements = new int[3];
    final boolean[] effects = new boolean[27];
    final boolean needsPermission;
    public Event(String name, Card card, boolean[] triggers, int[] requirements, boolean[] effects, boolean needsPermission){
        setName(name);
        Event.events.add(this);
        this.card = card;
        this.needsPermission = needsPermission;
        System.arraycopy(triggers, 0, this.triggers, 0, 14);
        System.arraycopy(requirements, 0, this.requirements, 0, 3);
        System.arraycopy(effects, 0, this.effects, 0, 27);
    }

    public static Card searchCardsOnEvent(Event event){
        for (Card card : Event.cards) {
            if (card.event.equals(event)) {
                return card;
            }
        }
        return null;
    }

    public static void addCard(Card card){
        cards.add(card);
        events.add(card.getEvent());
    }
    public static void removeCard(Card card){
        cards.remove(card);
        events.remove(card.getEvent());
    }

    public static ArrayList<Card> checkTriggersAndRequirements(String trigger){
        return getTriggeredCardsOnEvent(trigger);
    }

    public static ArrayList<Card> getTriggeredCardsOnEvent(String name){
        ArrayList<Card> answer = new ArrayList<>();
        for (Event event : Event.events) {
            if (event.name.equals(name)) {
                if (event.checkRequirements()) {
                    answer.add(event.card);
                }
            }
        }
        return answer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Event getRecentEvent(){
        return Event.events.get(Event.events.size() - 1);
    }

    public boolean checkRequirements(){
        if(requirements[0] != 0) {
            //sacrifice from hand
            return duel.getNumberOfCards("hand", false) >= requirements[0];

        } else if(requirements[1] != 0){
            //sacrifice from ground
            return duel.getNumberOfCards("monsterGround", false) >= requirements[1];

        } else{
            //beast king card
            if(requirements[2] == 0) {
                effect.call("decreaseAttackDamage", "1100");
                return true;
            } else if(requirements[2] == 3) {
                effect.call("destroyAllEnemyCards", "");
                return true;
            } else return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return Objects.equals(getName(), event.getName()) && Objects.equals(card, event.card);
    }
}

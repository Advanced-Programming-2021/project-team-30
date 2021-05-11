package model;

import model.cards.Card;

import java.util.ArrayList;
import java.util.Objects;

public class Event {
    private static ArrayList<Event> events;
    private String name;
    private static ArrayList<Card> cards;
    private Card card;
    public Event(String name){
        setName(name);
        events.add(this);
    }
    public static Card searchCardsOnEvent(Event event){
        for (Card card : Event.cards) {
            if (card.event.equals(event)) {
                return card;
            }
        }
        return null;
    }

    public static void addCard(Card card){ cards.add(card); }
    public static void removeCard(Card card){ cards.remove(card); }

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
    public ArrayList<Card> caller(String name){
        ArrayList<Card> answer = new ArrayList<>();
        for (Event event : Event.events) {
            if (event.name.equals(name)) {
                for (Card card : Event.cards) {
                    if (card.event.equals(event)) {
                        answer.add(card);
                    }
                }
            }
        }
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        Event event = (Event) o;
        return Objects.equals(getName(), event.getName()) && Objects.equals(card, event.card);
    }

}

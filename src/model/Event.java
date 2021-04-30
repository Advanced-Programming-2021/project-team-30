package model;

import java.util.ArrayList;

public class Event {
    private ArrayList<Event> events;
    private String name;
    private ArrayList<Card> cards;
    public Event(String name){
        setName(name);
    }
    public static void searchCardsOnEvent(Event event){

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
    public void getRecentEvent(){

    }
    public void caller(String name){

    }

}

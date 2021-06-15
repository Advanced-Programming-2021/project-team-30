package model.event;

import model.cards.Card;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import model.Duel;
import model.Dto;

public abstract class Event {

    private static ArrayList<Event> events;
    protected ArrayList<String>[] data;
    public boolean isCalled;

    public abstract Object decode(int index, int check);
    public abstract Event setParams(Dto data);
    public boolean checkParams(Dto data) {
        this.data[1] = data.getData();
        int size = this.data[1].size();
        if(size != this.data[0].size()) return false;
        for(int i = 0; i < size; i++)
            if(decode(i, 0) != decode(i, 1)) return false;
        return true;
    }
}

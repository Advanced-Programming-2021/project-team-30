package model.event;

import model.Dto;

import java.util.ArrayList;

public class OnSummon extends Event{
    private static OnSummon instance;
    private int location, ground;
    private boolean enemy, isSpecial;

    private OnSummon(){
        this.location = (int) decode(0);
        this.ground = (int) decode(1);
        this.enemy = (boolean) decode(2);
        this.isSpecial = (boolean) decode(3);
    }

    public Event setParams(Dto data){
        this.data = data.getData();
        if(instance == null) return instance = (new OnSummon());
        else{
            this.location = (int) decode(0);
            this.ground = (int) decode(1);
            this.enemy = (boolean) decode(2);
            this.isSpecial = (boolean) decode(3);
            return instance;
        }
    }

    @Override
    public Object decode(int index){
        return switch (index) {
            case 0, 1 -> Integer.parseInt(data.get(index));
            case 2, 3 -> Boolean.parseBoolean(data.get(index));
            default -> null;
        };
    }
}

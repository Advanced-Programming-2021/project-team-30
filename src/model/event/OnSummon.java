package model.event;

import model.Dto;

import java.util.ArrayList;

public class OnSummon extends Event{
    private static OnSummon instance;
    private int location, ground;
    private boolean enemy, isSpecial;

    public static int getLocation(){ return instance.location; }

    private OnSummon(){
        this.location = (int) decode(0, 0);
        this.ground = (int) decode(1, 0);
        this.enemy = (boolean) decode(2, 0);
        this.isSpecial = (boolean) decode(3, 0);
    }

    public Event setParams(Dto data){
        this.data[0] = data.getData();
        if(instance == null) return instance = (new OnSummon());
        else{
            instance.location = (int) decode(0, 0);
            instance.ground = (int) decode(1, 0);
            instance.enemy = (boolean) decode(2, 0);
            instance.isSpecial = (boolean) decode(3, 0);
            return instance;
        }
    }

    @Override
    public Object decode(int index, int check){
        return switch (index) {
            case 0, 1 -> Integer.parseInt(data[check].get(index));
            case 2, 3 -> Boolean.parseBoolean(data[check].get(index));
            default -> null;
        };
    }
}

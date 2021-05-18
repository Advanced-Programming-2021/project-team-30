package model.event;

import model.Dto;
import model.Ground;

public class OnFlip extends Event{
    private static OnFlip instance;
    private int location, player;
    private Ground ground;

    public static int getLocation(){ return instance.location; }

    private OnFlip(){
        this.location = (int) decode(0, 0);
        this.player = (int) decode(1, 0);
        this.ground = (Ground) decode(2, 0);
    }

    @Override
    public Event setParams(Dto data) {
        this.data[1] = data.getData();
        if(instance == null) return instance = new OnFlip();
        instance.location = (int) decode(0, 0);
        instance.player = (int) decode(1, 0);
        instance.ground = (Ground) decode(2, 0);
        return instance;
    }

    @Override
    public Object decode(int index, int check) {
        return switch (index) {
            case 0, 1 -> Integer.parseInt(data[check].get(index));
            case 2 -> Ground.valueOf(data[check].get(index));
            default -> null;
        };
    }
}

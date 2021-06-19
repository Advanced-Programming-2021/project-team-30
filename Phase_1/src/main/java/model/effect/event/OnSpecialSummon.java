package model.effect.event;

import model.Dto;
import model.Ground;

public class OnSpecialSummon extends Event{
    private static OnSpecialSummon instance;
    public static boolean isCalled;
    private Ground ground;
    private int location, player;
    @Override
    public Object decode(int index, int check) {
        return switch (index) {
            case 0, 2 -> Integer.parseInt(data[check].get(index));
            case 1 -> Ground.valueOf(data[check].get(index));
            default -> null;
        };
    }

    @Override
    public Event setParams(Dto data) {
        this.data[0] = data.getData();
        if(instance == null) return instance = (new OnSpecialSummon());
        else{
            instance.location = (int) decode(0, 0);
            instance.ground = (Ground) decode(1, 0);
            instance.player = (int) decode(2, 0);
            return instance;
        }
    }
}

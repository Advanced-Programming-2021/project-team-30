package model.effect.event;

import model.Dto;
import model.Ground;


public class OnSummon extends Event{
    private static OnSummon instance;
    public static boolean isCalled;
    private Ground ground;
    private int location, player;
    private boolean isSpecial;

    public static int getLocation(){ return instance.location; }
    public static Ground getGround(){ return instance.ground; }
    public static int getPlayer(){ return instance.player; }
    public static boolean isSummonedSpecial(){ return instance.isSpecial; }

    private OnSummon(){
        this.location = (int) decode(0, 0);
        this.ground = (Ground) decode(1, 0);
        this.player = (int) decode(2, 0);
        this.isSpecial = (boolean) decode(3, 0);
    }

    public static OnSummon getInstance() {
        return instance;
    }

    public Event setParams(Dto data){
        this.data[0] = data.getData();
        if(instance == null) return instance = (new OnSummon());
        else{
            instance.location = (int) decode(0, 0);
            instance.ground = (Ground) decode(1, 0);
            instance.player = (int) decode(2, 0);
            instance.isSpecial = (boolean) decode(3, 0);
            return instance;
        }
    }

    @Override
    public Object decode(int index, int check){
        return switch (index) {
            case 0, 2 -> Integer.parseInt(data[check].get(index));
            case 1 -> Ground.valueOf(data[check].get(index));
            case 3 -> Boolean.parseBoolean(data[check].get(index));
            default -> null;
        };
    }
}

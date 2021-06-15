package model.event;

import model.Dto;

public class OnDeath extends Event{
    private static OnDeath instance;
    public static boolean isCalled;
    private int location;
    private boolean isDoneByEffect;

    public static OnDeath getInstance(){
        return instance;
    }

    public static int getLocation(){ return instance.location; }

    public OnDeath(){
        this.location = (int)decode(0, 0);
        this.isDoneByEffect = (boolean)decode(1, 0);
    }

    @Override
    public Event setParams(Dto data){
        this.data[0] = data.getData();
        if(instance == null) return instance = (new OnDeath());
        instance.location = (int)decode(0, 0);
        instance.isDoneByEffect = (boolean)decode(1, 0);
        return instance;
    }

    @Override
    public Object decode(int index, int check){
        return switch (index) {
            case 0 -> Integer.parseInt(data[check].get(index));
            case 1 -> Boolean.parseBoolean(data[check].get(index));
            default -> null;
        };
    }
}

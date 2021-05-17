package model.event;

import model.Dto;

public class OnDeath extends Event{
    private static OnDeath instance;
    private int location;
    private boolean isDoneByEffect;

    public OnDeath(){
        this.location = (int)decode(0);
        this.isDoneByEffect = (boolean)decode(1);
    }

    @Override
    public Event setParams(Dto data){
        this.data = data.getData();
        if(instance == null) return instance = (new OnDeath());
        this.location = (int)decode(0);
        this.isDoneByEffect = (boolean)decode(1);
        return instance;
    }

    @Override
    public Object decode(int index){
        return switch (index) {
            case 0 -> Integer.parseInt(data.get(index));
            case 1 -> Boolean.parseBoolean(data.get(index));
            default -> null;
        };
    }
}

package model.event;

import model.Dto;

public class OnSpellActivation extends Event{
    private static OnSpellActivation instance;
    private int location;
    boolean enemy;

    private OnSpellActivation(){
        this.location = (int)decode(0);
        this.enemy = (boolean)decode(1);
    }

    @Override
    public Event setParams(Dto data) {
        this.data = data.getData();
        if(instance == null) return instance = new OnSpellActivation();
        this.location = (int)decode(0);
        this.enemy = (boolean)decode(1);
        return instance;
    }

    @Override
    public Object decode(int index) {
        return switch (index) {
            case 0 -> Integer.parseInt(data.get(index));
            case 1 -> Boolean.parseBoolean(data.get(index));
            default -> null;
        };
    }
}

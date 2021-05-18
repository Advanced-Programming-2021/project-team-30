package model.event;

import model.Dto;

public class OnSpellActivation extends Event{
    private static OnSpellActivation instance;
    private int location;
    boolean enemy;


    private OnSpellActivation(){
        this.location = (int)decode(0, 0);
        this.enemy = (boolean)decode(1, 0);
    }

    @Override
    public Event setParams(Dto data) {
        this.data[0] = data.getData();
        if(instance == null) return instance = new OnSpellActivation();
        instance.location = (int)decode(0, 0);
        instance.enemy = (boolean)decode(1, 0);
        return instance;
    }

    @Override
    public Object decode(int index, int check) {
        return switch (index) {
            case 0 -> Integer.parseInt(data[check].get(index));
            case 1 -> Boolean.parseBoolean(data[check].get(index));
            default -> null;
        };
    }
}

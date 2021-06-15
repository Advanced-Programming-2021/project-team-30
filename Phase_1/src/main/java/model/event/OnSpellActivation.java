package model.event;

import model.Dto;

public class OnSpellActivation extends Event{
    private static OnSpellActivation instance;
    public static boolean isCalled;
    private int location, player;
    private boolean causesDamage;

    public static int getLocation(){ return instance.location; }

    public static int getPlayer(){ return instance.player; }

    public static boolean doesCauseDamage(){ return instance.causesDamage; }

    private OnSpellActivation(){
        this.location = (int)decode(0, 0);
        this.player = (int)decode(1, 0);
        this.causesDamage = (boolean) decode(2, 0);
    }

    public static OnSpellActivation getInstance() {
        return instance;
    }

    @Override
    public Event setParams(Dto data) {
        this.data[0] = data.getData();
        if(instance == null) return instance = new OnSpellActivation();
        instance.location = (int)decode(0, 0);
        instance.player = (int)decode(1, 0);
        instance.causesDamage = (boolean)decode(2, 0);
        return instance;
    }

    @Override
    public Object decode(int index, int check) {
        return switch (index) {
            case 0, 1 -> Integer.parseInt(data[check].get(index));
            case 2 -> Boolean.parseBoolean(data[check].get(index));
            default -> null;
        };
    }
}

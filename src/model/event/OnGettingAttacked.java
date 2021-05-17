package model.event;

import model.Dto;

public class OnGettingAttacked extends Event{
    private static OnGettingAttacked instance;
    private int location;

    private OnGettingAttacked(){
        this.location = (int)decode(0);
    }

    @Override
    public Event setParams(Dto data){
        this.data = data.getData();
        if(instance == null) return instance = (new OnGettingAttacked());
        instance.location = (int)decode(0);
        return instance;
    }

    @Override
    public Object decode(int index){
        if(index == 0) return Integer.parseInt(data.get(0));
        return null;
    }
}

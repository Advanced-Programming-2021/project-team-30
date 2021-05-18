package model.event;

import model.Dto;

public class OnStandByPhase extends Event{
    private OnStandByPhase instance;
    

    @Override
    public Event setParams(Dto data) {
        if(instance == null) return instance = new OnStandByPhase();
        return instance;
    }

    public Object decode(int index, int check){
        return null;
    }
}

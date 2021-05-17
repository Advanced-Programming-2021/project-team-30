package model.event;

import model.Dto;

public class OnEnemyBattlePhase extends Event{
    private static OnEnemyBattlePhase instance;

    @Override
    public Event setParams(Dto data) {
        if(instance == null) return instance = (new OnEnemyBattlePhase());
        return instance;
    }

    @Override
    public Object decode(int index){
        return null;
    }
}
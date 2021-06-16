package model.effect.requirements;

import model.Ground;

public class MinNumberOfFriends extends Requirement{
    final Ground ground;
    final int minRequired;

    public MinNumberOfFriends(Ground ground, int minRequired){
        this.ground = ground;
        this.minRequired = minRequired;
    }

    @Override
    public boolean check(){
        return duel.getNumberOfCards(Ground.monsterGround, duel.getCurrentPlayer()) >= minRequired;
    }
}

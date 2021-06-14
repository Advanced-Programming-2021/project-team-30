package model.effect;

import model.Duel;
import model.Ground;

public class DestroyRecentAttacker extends Effect{

    public DestroyRecentAttacker(int ownerPlayer){
        super(ownerPlayer);
    }

    @Override
    public void doEffect() {
        duel.killCard(Duel.attackerLocation, Ground.monsterGround, 1 - ownerPlayer);
    }

    @Override
    public void undoEffect() {

    }
}

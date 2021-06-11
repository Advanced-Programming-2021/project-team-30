package model.effect;

public class BlockAttacker extends Effect{

    @Override
    public void doEffect(){
        duel.blockAttack();
    }

    @Override
    public void undoEffect(){}
}

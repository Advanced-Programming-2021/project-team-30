package model.effect.action;

import model.Ground;
import java.util.ArrayList;

public class AddAttackDamage extends Action {
    final int damage, location;
    final ArrayList<Ground> targetGrounds;
    final boolean doForBoth;

    public AddAttackDamage(int cardOwner,int damage, int location, ArrayList<Ground> targetGrounds, boolean doForBoth){
        super(cardOwner);
        this.damage = damage;
        this.location = location;
        this.targetGrounds = targetGrounds;
        this.doForBoth = doForBoth;
    }

    @Override
    public void doEffect(){
        if(location != -1){
            duel.addAttackDamage(location, duel.getCurrentPlayer(), damage);
            if(doForBoth) duel.addAttackDamage(location, 1 - duel.getCurrentPlayer(), damage);
        } else{
            duel.addAttackDamageToGround(duel.getCurrentPlayer(), damage);
            if(doForBoth) duel.addAttackDamageToGround(1 - duel.getCurrentPlayer(), damage);
        }
    }

    @Override
    public void undoEffect(){
        if(location != -1){
            duel.addAttackDamage(location, duel.getCurrentPlayer(), -1 * damage);
            if(doForBoth) duel.addAttackDamage(location, 1 - duel.getCurrentPlayer(), -1 * damage);
        } else{
            duel.addAttackDamageToGround(duel.getCurrentPlayer(), -1 * damage);
            if(doForBoth) duel.addAttackDamageToGround(1 - duel.getCurrentPlayer(), -1 * damage);
        }
    }

    @Override
    public void callEvent(boolean activationStatus){}
}

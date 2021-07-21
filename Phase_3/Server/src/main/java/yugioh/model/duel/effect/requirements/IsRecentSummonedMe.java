package yugioh.model.duel.effect.requirements;

import yugioh.model.duel.effect.event.OnSummon;

public class IsRecentSummonedMe extends Requirement{
    public IsRecentSummonedMe(int myLocation, int ownerPlayer){
        super(ownerPlayer);
        this.myLocation = myLocation; }

    @Override
    public boolean check(){ return OnSummon.getLocation() == myLocation; }
}

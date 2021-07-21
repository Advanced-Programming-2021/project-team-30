package yugioh.model.duel.effect.requirements;

import yugioh.model.duel.effect.event.OnGettingAttacked;

public class IsRecentAttackedMe extends Requirement{
    public IsRecentAttackedMe(int myLocation, int ownerPlayer){
        super(ownerPlayer);
        this.myLocation = myLocation;
    }

    public boolean check(){ return OnGettingAttacked.getLocation() == myLocation; }
}

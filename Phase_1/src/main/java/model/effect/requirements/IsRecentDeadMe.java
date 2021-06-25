package model.effect.requirements;

import model.effect.event.OnDeath;

public class IsRecentDeadMe extends Requirement{
    final int myLocation;

    public IsRecentDeadMe(int myLocation, int ownerPlayer){
        super(ownerPlayer);
        this.myLocation = myLocation; }

    public boolean check(){ return myLocation == OnDeath.getLocation(); }
}

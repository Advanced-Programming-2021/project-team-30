package model.effect.requirements;

import model.effect.event.OnSummon;

public class IsRecentSummonedMe extends Requirement{
    public IsRecentSummonedMe(int myLocation, int ownerPlayer){
        super(ownerPlayer);
        this.myLocation = myLocation; }

    @Override
    public boolean check(){ return OnSummon.getLocation() == myLocation; }
}

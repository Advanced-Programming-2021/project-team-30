package model.requirements;

import model.event.OnSummon;

public class IsRecentSummonedMe extends Requirement{
    final int myLocation;

    public IsRecentSummonedMe(int myLocation){ this.myLocation = myLocation; }

    @Override
    public boolean check(){ return OnSummon.getLocation() == myLocation; }
}

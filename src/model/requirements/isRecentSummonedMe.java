package model.requirements;

import model.event.OnSummon;

public class isRecentSummonedMe extends Requirement{
    final int myLocation;

    public isRecentSummonedMe(int myLocation){ this.myLocation = myLocation; }

    @Override
    public boolean check(){ return OnSummon.getLocation() == myLocation; }
}

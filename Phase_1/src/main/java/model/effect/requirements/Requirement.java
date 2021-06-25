package model.effect.requirements;

import model.Duel;

public abstract class Requirement {
    final static Duel duel = Duel.getRecentDuel();
    protected int myLocation, ownerPlayer;

    public Requirement(int ownerPlayer){
        this.ownerPlayer = ownerPlayer;
    }

    public void setMyLocation(int myLocation){ this.myLocation = myLocation; }

    public abstract boolean check();
}

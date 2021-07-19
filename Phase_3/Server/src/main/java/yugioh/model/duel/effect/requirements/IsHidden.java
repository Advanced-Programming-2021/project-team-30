package model.effect.requirements;

import model.Ground;

public class IsHidden extends Requirement{
    public int myLocation;
    public Ground myGround;

    public IsHidden(int myLocation, Ground myGround, int ownerPlayer){
        super(ownerPlayer);
        this.myLocation = myLocation;
        this.myGround = myGround;
    }

    public boolean check(){
       String pos = duel.getPosition(duel.getCurrentPlayer(), myLocation, myGround);
       return pos.equals("DH") || pos.equals("H") || pos.equals("OH");
    }
}

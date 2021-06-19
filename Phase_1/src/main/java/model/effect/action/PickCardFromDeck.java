package model.effect.action;

import model.Ground;
import model.cards.Type;
import model.response.DuelMenuResponse;
import view.Main;

import java.util.ArrayList;

public class PickCardFromDeck extends Action{
    final ArrayList<Ground> grounds;
    final ArrayList<Type> types;
    final int total;
    public PickCardFromDeck(int ownerPlayer, boolean canBeUsedOncePerRound, ArrayList<Ground> grounds, ArrayList<Type> types, int total) {
        super(ownerPlayer, canBeUsedOncePerRound);
        this.grounds = grounds;
        this.types = types;
        this.total = total;
    }

    @Override
    public void doEffect() {
        int location, total = this.total;
        while(total-- > 0){
            while(true){
                try{
                    location = Integer.parseInt(duel.listen());
                    break;
                }catch (Exception e){
                    Main.outputToUser(DuelMenuResponse.invalidInput);
                }
            }
            duel.addCard(ownerPlayer, Ground.handGround, duel.getCard(Ground.mainDeckGround, location, ownerPlayer), "");
            duel.removeCard(ownerPlayer, Ground.mainDeckGround, location);
        }
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {

    }
}

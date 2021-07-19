package yugioh.model.duel.effect.action;

import yugioh.model.duel.Ground;
import yugioh.model.duel.response.Response;


public class DestroyCardWithGuessedName extends Action{
    public DestroyCardWithGuessedName(int ownerPlayer, boolean canBeUsedOncePerRound, String ownerCard) {
        super(ownerPlayer, canBeUsedOncePerRound, ownerCard);
    }

    @Override
    public void doEffect() {
        Response.writeMessage(ownerPlayer, "Guess a card's name");
        String ask = Main.getInput();
        boolean find = false;
        for(int location = 0; location < duel.getNumberOfCards(Ground.handGround, 1 - ownerPlayer); location++)
            if(duel.getCard(Ground.handGround, location, 1 - ownerPlayer).getName().equals(ask)){
                find = true;
                break;
            }
        if(find)
            for(int location = 0; location < duel.getNumberOfCards(Ground.handGround, 1 - ownerPlayer); location++)
                if(duel.getCard(Ground.handGround, location, 1 - ownerPlayer).getName().equals(ask))
                    duel.killCard(location, Ground.handGround, 1 - ownerPlayer);
        else
            duel.killCard(-1, Ground.handGround, ownerPlayer);
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}

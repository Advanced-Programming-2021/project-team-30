package yugioh.model.duel.effect.action;

import yugioh.model.duel.Command;
import yugioh.model.duel.Ground;
import yugioh.model.duel.response.Response;
import java.util.regex.Matcher;


public class DestroyEnemySpells extends Action{
    final int totalCards;
    public DestroyEnemySpells(int ownerPlayer, boolean canBeUsedOncePerRound, String cardName, int totalCards) {
        super(ownerPlayer, canBeUsedOncePerRound, cardName);
        this.totalCards = totalCards;
    }

    @Override
    public void doEffect() {
//        int x = totalCards;
//        Matcher matcher;
//        int location;
//        while(x > 0) {
//            matcher = DuelMenuRegex.getInputForCommand(Command.selectSpell);
//            location = Integer.parseInt(matcher.group(1)) - 1;
//            if(duel.getCard(Ground.spellTrapGround, location, 1 - ownerPlayer) == null) {
//                Main.outputToUser(Response.noCardFound);
//                continue;
//            }
//            duel.removeCard(1 - ownerPlayer, Ground.spellTrapGround, location);
//            x--;
//        }
    }

    @Override
    public void undoEffect() {}

    @Override
    public void callEvent(boolean activationStatus) {}
}

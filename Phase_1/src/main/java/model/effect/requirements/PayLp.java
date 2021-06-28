package model.effect.requirements;

import model.regex.DuelMenuRegex;


public class PayLp extends Requirement{
    final int lp;
    public PayLp(int ownerPlayer, int lp) {
        super(ownerPlayer);
        this.lp = lp;
    }

    @Override
    public boolean check() {
        String question = String.format("will you pay <%d> life points for the card's effect?", lp);
        String ask = DuelMenuRegex.getDesiredInput(question, new String[]{
                "yes", "no"
        });
        if(ask.equals("no"))
            return false;
        duel.decreaseLp(lp, duel.getCurrentPlayer());
        return true;
    }
}

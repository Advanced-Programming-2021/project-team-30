package yugioh.model.duel.effect.requirements;

import yugioh.model.duel.effect.event.OnSpellActivation;

public class DoesRecentSpellCauseDamage extends Requirement{

    public DoesRecentSpellCauseDamage(int ownerPlayer) {
        super(ownerPlayer);
    }

    public boolean check(){
        if(OnSpellActivation.getPlayer() == duel.getCurrentPlayer()) return false;

        return OnSpellActivation.doesCauseDamage();
    }
}

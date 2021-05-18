package model.requirements;

import model.event.OnSpellActivation;

public class DoesRecentSpellCauseDamage extends Requirement{

    public boolean check(){
        if(OnSpellActivation.getPlayer() == duel.getCurrentPlayer()) return false;

        return OnSpellActivation.doesCauseDamage();
    }
}

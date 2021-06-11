package model.cards.MonsterCard;

import java.util.ArrayList;

public class EffectCard extends MonsterCard  {
        public ArrayList<String> effect = new ArrayList<>() ;
        public ArrayList<String> target = new ArrayList<>() ;
    public void EffectCard(){
        // ?
    }
    @Override
    public void activeCard() {
        super.activeCard();
    }
    public boolean checkRitualSpellCard(){
        return true ;
    }
}

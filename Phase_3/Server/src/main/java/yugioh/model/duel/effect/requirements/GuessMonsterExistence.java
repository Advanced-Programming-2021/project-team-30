package yugioh.model.duel.effect.requirements;

import yugioh.model.duel.Ground;

public class GuessMonsterExistence extends Requirement{

    private String name;

    public GuessMonsterExistence(int ownerPlayer) {
        super(ownerPlayer);
    }

    public void setName(String name){ this.name = name; }

    @Override
    public boolean check() { return duel.doesCardWithNameExist(Ground.mainDeckGround, duel.getCurrentPlayer(), name); }
}

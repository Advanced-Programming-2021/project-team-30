package model.effect.requirements;

import model.Ground;

public class GuessMonsterExistence extends Requirement{

    private String name;

    public void setName(String name){ this.name = name; }

    @Override
    public boolean check() { return duel.doesCardWithNameExist(Ground.mainDeckGround, duel.getCurrentPlayer(), name); }
}

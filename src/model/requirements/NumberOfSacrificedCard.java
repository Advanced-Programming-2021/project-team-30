package model.requirements;

public class NumberOfSacrificedCard extends Requirement{
    final int neededCards;
    private int sacrificed;

    public NumberOfSacrificedCard(int neededCards){ this.neededCards = neededCards; }

    public void setSacrificed(int sacrificed){ this.sacrificed = sacrificed; }

    public boolean check(){ return this.neededCards == this.sacrificed; }
}

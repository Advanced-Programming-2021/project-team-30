package model.effect.requirements;

import model.Ground;
import model.regex.DuelMenuRegex;
import model.response.DuelMenuResponse;
import view.Main;

import java.util.ArrayList;

public class SacrificeCards extends Requirement{
    final int neededCards;
    final ArrayList<Ground> grounds;
    final ArrayList<String> groundStrings = new ArrayList<>();

    public SacrificeCards(int neededCards, ArrayList<Ground> grounds, int ownerPlayer){
        super(ownerPlayer);
        this.neededCards = neededCards;
        this.grounds = grounds;
        for(Ground ground: grounds)
            groundStrings.add(ground.toString());
    }

    public boolean check(){
        boolean[] mark = {false, false, false, false, false};
        int x = neededCards;
        Main.outputToUser("please select the cards you like for sacrifice\noptions:");
        for(Ground ground: grounds)
            Main.outputToUser(ground.toString());
        while (x > 0){
            Ground ground = Ground.valueOf(DuelMenuRegex.getDesiredInput("which ground?", (String[])groundStrings.toArray()));
            int location = Integer.parseInt(DuelMenuRegex.getDesiredInput("location?", new String[]{
                    "1", "2", "3", "4", "5"
            })) - 1;
            if(duel.getCard(ground, location, ownerPlayer) == null) {
                Main.outputToUser(DuelMenuResponse.noCardFound);
                continue;
            }
            else if(mark[location]){
                Main.outputToUser("Card is already selected");
                continue;
            }
            x--;
            mark[location] = true;
        }
        //TODO developing this one
    }
}

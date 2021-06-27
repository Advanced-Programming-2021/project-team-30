package model.effect.requirements;

import model.Ground;
import model.regex.DuelMenuRegex;
import model.response.DuelMenuResponse;
import view.Main;

import java.util.ArrayList;

public class SacrificeCards extends Requirement{
    final int neededCards;
    final ArrayList<Ground> grounds;

    public SacrificeCards(int ownerPlayer, int neededCards, ArrayList<Ground> grounds){
        super(ownerPlayer);
        this.neededCards = neededCards;
        this.grounds = grounds;
    }

    public boolean check(){
        boolean[] mark = {false, false, false, false, false};
        int x = neededCards;
        Main.outputToUser("please select the cards you like for sacrifice\noptions:");
        while (x > 0){
            int location;
            try{
                location = Integer.parseInt(DuelMenuRegex.getDesiredInput("location?", new String[]{
                        "1", "2", "3", "4", "5", "cancel"
                })) - 1;
            }catch (Exception e){
                Main.outputToUser("sacrifice canceled");
                return false;
            }
            if(duel.getCard(Ground.monsterGround, location, ownerPlayer) == null) {
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
        for(int location = 0; location < 5; location++)
            if(mark[location])
                duel.killCard(location, Ground.monsterGround, ownerPlayer);
        Main.outputToUser("sacrifice done successfully");
        return true;
    }
}

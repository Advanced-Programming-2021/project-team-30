package model.phase;

import model.Command;

public class BattlePhase extends Phase {
    public BattlePhase(){

        validCommands = new Command[]{
                Command.nextPhase,
                Command.surrender,
                Command.attack,
                Command.directAttack,
                Command.deselect,
                Command.selectField,
                Command.selectHand,
                Command.selectMonster,
                Command.selectSpell,
                Command.selectOpponentField,
                Command.selectOpponentMonster,
                Command.selectOpponentSpell,
                Command.cardShow,
                Command.cardShowSelected
        };
    }
}

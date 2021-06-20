package model.phase;

import model.Command;

public class MainPhase2 extends Phase {
    public MainPhase2(){
        validCommands = new Command[]{
                Command.nextPhase,
                Command.surrender,
                Command.deselect,
                Command.selectField,
                Command.selectHand,
                Command.selectMonster,
                Command.selectSpell,
                Command.selectOpponentField,
                Command.selectOpponentMonster,
                Command.selectOpponentSpell,
                Command.cardShow,
                Command.cardShowSelected,
                Command.activateEffect,
                Command.summon,
                Command.ritualSummon,
                Command.flipSummon,
                Command.showGraveyard,
                Command.setCardPositionAttack,
                Command.setCardPositionDefense,
                Command.set
        };
    }
}


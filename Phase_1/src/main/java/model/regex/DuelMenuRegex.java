package model.regex;

import model.Command;
import model.DuelMenuCommand;
import model.response.DuelMenuResponse;
import view.Main;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DuelMenuRegex {
    public static String[] newDuel = {
            "duel --new --second-player (?<player2Name>.*) --rounds (?<round>\\d)",
            "duel --new --rounds (?<round>\\d) --second-player (?<player2Name>.*)",
            "duel --second-player (?<player2Name>.*) --new --rounds (?<round>\\d)",
            "duel --rounds (?<round>\\d) --new --second-player (?<player2Name>.*)",
            "duel --second-player (?<player2Name>.*) --rounds (?<round>\\d) --new",
            "duel --rounds (?<round>\\d) --second-player (?<player2Name>.*) --new"
    };
    public static String[] newDuelAbbr = {
            "duel -n -s-p (?<player2Name>.*) -r (?<round>\\d)",
            "duel -n -r (?<round>\\d) -s-p (?<player2Name>.*)",
            "duel -s-p (?<player2Name>.*) -n -r (?<round>\\d)",
            "duel -r (?<round>\\d) -n -s-p (?<player2Name>.*)",
            "duel -s-p (?<player2Name>.*) -r (?<round>\\d) -n",
            "duel -r (?<round>\\d) -s-p (?<player2Name>.*) -n"
    };
    public static String[] newDuelAI = {
            "duel --new --ai --rounds (?<round>\\d)",
            "duel --new --rounds (?<round>\\d) --ai",
            "duel --ai --new --rounds (?<round>\\d)",
            "duel --rounds (?<round>\\d) --new --ai",
            "duel --ai --rounds (?<round>\\d) --new",
            "duel --rounds (?<round>\\d) --ai --new"
    };
    public static String[] newDuelAIAbbr = {
            "duel -n -ai -r (?<round>\\d)",
            "duel -n -r (?<round>\\d) -ai",
            "duel -ai -n -r (?<round>\\d)",
            "duel -r (?<round>\\d) -n -ai",
            "duel -ai -r (?<round>\\d) -n",
            "duel -r (?<round>\\d) -ai -n"
    };

    public static HashMap<Command, String[]> patterns = new HashMap<>(){{
        //TODO fixing regexes
        put(Command.cardShow, new String[]{
                "card show (?<cardName>.*)"
        });
        put(Command.selectMonster, new String[]{
                "\\s*select --monster (\\d)\\s*",
                "\\s*select -m (\\d)\\s*"
        });
        put(Command.selectOpponentMonster, new String[]{
                "\\s*select --monster --opponent (\\d)\\s*",
                "\\s*select --opponent --monster (\\d)\\s*",
                "\\s*select -m -o (\\d)\\s*",
                "\\s*select -o -m (\\d)\\s*"
        });
        put(Command.selectSpell, new String[]{
                "\\s*select --spell (\\d)\\s*",
                "\\s*select -s (\\d)\\s*"
        });
        put(Command.selectOpponentSpell, new String[]{
                "\\s*select --spell --opponent (\\d)\\s*",
                "\\s*select --opponent --spell (\\d)\\s*",
                "\\s*select -s -o (\\d)\\s*",
                "\\s*select -o -s (\\d)\\s*"
        });
        put(Command.selectField, new String[]{
                "\\s*select --field\\s*",
                "\\s*select -f\\s*"
        });
        put(Command.selectOpponentField, new String[]{
                "\\s*select --field --opponent\\s*",
                "\\s*select --opponent --field\\s*",
                "\\s*select -f -o\\s*",
                "\\s*select -o -f\\s*"
        });
        put(Command.selectHand, new String[]{
                "\\s*select --hand (\\d)\\s*",
                "\\s*select -h (\\d)\\s*"
        });
        put(Command.deselect, new String[]{
                "\\s*select -d\\s*"
        });
        put(Command.setCardPositionDefense, new String[]{
                "\\s*set --position defense\\s*",
                "\\s*set -p defense\\s*"
        });
        put(Command.setCardPositionAttack, new String[]{
                "\\s*set --position attack\\s*",
                "\\s*set -p attack\\s*"
        });
        put(Command.attack, new String[]{
                "\\s*attack (\\d)\\s*"
        });
        put(Command.set, new String[]{
                "\\s*set\\s*"
        });
        put(Command.cardShowSelected, new String[]{
                "\\s*card show --selected\\s*",
                "\\s*card show -s\\s*"
        });
        put(Command.nextPhase, new String[]{
                "\\s*next phase\\s*"
        });
        put(Command.summon, new String[]{
                "\\s*summon\\s*"
        });
        put(Command.flipSummon, new String[]{
                "\\s*flip-summon\\s*"
        });
        put(Command.directAttack, new String[]{
                "\\s*attack direct\\s*"
        });
        put(Command.activateEffect, new String[]{
                "\\s*activate effect\\s*"
        });
    }};

    public static String getDesiredInput(String question, String[] desiredOutputs){
        Main.outputToUser(question + "\n(");
        for(String desiredOutput: desiredOutputs)
            Main.outputToUser(desiredOutput);
        Main.outputToUser(")");
        String input;
        while(true){
            input = Main.getInput();
            for(String desiredOutput: desiredOutputs)
                if(desiredOutput.equals(input))
                    return desiredOutput;
            Main.outputToUser(DuelMenuResponse.invalidInput);
        }
    }

    public static void setCommandValues(String input){
        Pattern pattern;
        Matcher matcher;
        String[] regexes;
        for(Command key: patterns.keySet()){
            regexes = patterns.get(key);
            for(String regex: regexes){
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(input);
                if(matcher.find()) {
                    DuelMenuCommand.setParams(key, pattern.matcher(input));
                    return;
                }
            }
        }
        Main.outputToUser(DuelMenuResponse.invalidInput);
    }
}

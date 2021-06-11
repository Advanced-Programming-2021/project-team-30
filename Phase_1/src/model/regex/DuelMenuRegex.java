package model.regex;

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

    public static String cardShow = "card show (?<cardName>.*)";
    public static String selectMonster = "\\s*select --monster (\\d)\\s*";
    public static String selectMonsterAbbr = "\\s*select -m (\\d)\\s*";
    public static String[] selectOpponentMonster = {
            "\\s*select --monster --opponent (\\d)\\s*",
            "\\s*select --opponent --monster (\\d)\\s*"
    };
    public static String[] selectOpponentMonsterAbbr = {
            "\\s*select -m -o (\\d)\\s*",
            "\\s*select -o -m (\\d)\\s*"
    };
    public static String selectSpell = "\\s*select --spell (\\d)\\s*";
    public static String selectSpellAbbr = "\\s*select -s (\\d)\\s*";
    public static String[] selectOpponentSpell = {
            "\\s*select --spell --opponent (\\d)\\s*",
            "\\s*select --opponent --spell (\\d)\\s*"
    };
    public static String[] selectOpponentSpellAbbr = {
            "\\s*select -s -o (\\d)\\s*",
            "\\s*select -o -s (\\d)\\s*"
    };
    public static String selectField = "\\s*select --field\\s*";
    public static String selectFieldAbbr = "\\s*select -f\\s*";
    public static String[] selectOpponentField = {
            "\\s*select --field --opponent\\s*",
            "\\s*select --opponent --field\\s*"
    };
    public static String[] selectOpponentFieldAbbr = {
            "\\s*select -f -o\\s*",
            "\\s*select -o -f\\s*"
    };
    public static String selectHand = "\\s*select --hand (\\d)\\s*";
    public static String selectHandAbbr = "\\s*select -h (\\d)\\s*";
    public static String deselect = "\\s*select -d\\s*";
    public static String setCardPosition = "\\s*set --position (attack|defense)\\s*";
    public static String setCardPositionAbbr = "\\s*set -p (attack|defense)\\s*";
    public static String attack = "\\s*attack (\\d)\\s*";
    public static String cardShowSelected = "\\s*card show --selected\\s*";
    public static String cardShowSelectedAbbr = "\\s*card show -s\\s*";


}

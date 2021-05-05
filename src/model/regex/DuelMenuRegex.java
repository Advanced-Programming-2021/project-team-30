package model.regex;

public class DuelMenuRegex {
    public static String[] newDuel = {
            "\\s*duel --new --second-player \\s*(?<player2Name>.*)\\s* --rounds \\s*(?<round>\\d)\\s*",
            "\\s*duel --new --rounds \\s*(?<round>\\d)\\s* --second-player \\s*(?<player2Name>.*)\\s*",
            "\\s*duel --second-player \\s*(?<player2Name>.*)\\s* --new --rounds \\s*(?<round>\\d)\\s*",
            "\\s*duel --rounds \\s*(?<round>\\d)\\s* --new --second-player \\s*(?<player2Name>.*)\\s*",
            "\\s*duel --second-player \\s*(?<player2Name>.*)\\s* --rounds \\s*(?<round>\\d)\\s* --new\\s*",
            "\\s*duel --rounds \\s*(?<round>\\d)\\s* --second-player \\s*(?<player2Name>.*)\\s* --new\\s*"
    };
    public static String[] newDuelAbbr = {
            "\\s*duel -n -s-p \\s*(?<player2Name>.*)\\s* -r \\s*(?<round>\\d)\\s*",
            "\\s*duel -n -r \\s*(?<round>\\d)\\s* -s-p \\s*(?<player2Name>.*)\\s*",
            "\\s*duel -s-p \\s*(?<player2Name>.*)\\s* -n -r \\s*(?<round>\\d)\\s*",
            "\\s*duel -r \\s*(?<round>\\d)\\s* -n -s-p \\s*(?<player2Name>.*)\\s*",
            "\\s*duel -s-p \\s*(?<player2Name>.*)\\s* -r \\s*(?<round>\\d)\\s* -n\\s*",
            "\\s*duel -r \\s*(?<round>\\d)\\s* -s-p \\s*(?<player2Name>.*)\\s* -n\\s*"
    };
    public static String[] newDuelAI = {
            "\\s*duel --new --ai --rounds \\s*(?<round>\\d)\\s*",
            "\\s*duel --new --rounds \\s*(?<round>\\d)\\s* --ai\\s*",
            "\\s*duel --ai --new --rounds \\s*(?<round>\\d)\\s*",
            "\\s*duel --rounds \\s*(?<round>\\d)\\s* --new --ai\\s*",
            "\\s*duel --ai --rounds \\s*(?<round>\\d)\\s* --new\\s*",
            "\\s*duel --rounds \\s*(?<round>\\d)\\s* --ai --new\\s*"
    };
    public static String[] newDuelAIAbbr = {
            "\\s*duel -n -ai -r \\s*(?<round>\\d)\\s*",
            "\\s*duel -n -r \\s*(?<round>\\d)\\s* -ai\\s*",
            "\\s*duel -ai -n -r \\s*(?<round>\\d)\\s*",
            "\\s*duel -r \\s*(?<round>\\d)\\s* -n -ai\\s*",
            "\\s*duel -ai -r \\s*(?<round>\\d)\\s* -n\\s*",
            "\\s*duel -r \\s*(?<round>\\d)\\s* -ai -n\\s*"
    };

    public static String cardShow = "\\s*card show \\s*(?<cardName>.*)\\s*";
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

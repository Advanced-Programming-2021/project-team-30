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
    public static String selectMonster;
    public static String selectMonsterAbbr;
    public static String[] selectOpponentMonster;
    public static String[] selectOpponentMonsterAbbr;
    public static String selectSpell;
    public static String selectSpellAbbr;
    public static String[] selectOpponentSpell;
    public static String[] selectOpponentSpellAbbr;
    public static String selectField;
    public static String selectFieldAbbr;
    public static String[] selectOpponentField;
    public static String[] selectOpponentFieldAbbr;
    public static String selectHand;
    public static String selectHandAbbr;
    public static String deselect;
    public static String setCardPosition;
    public static String setCardPositionAbbr;
    public static String attack;
    public static String cardShowSelected;
    public static String cardShowSelectedAbbr;


}

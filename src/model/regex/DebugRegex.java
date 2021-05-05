package model.regex;

public class DebugRegex {
    public static String increaseMoney = "\\s*increase --money (?<amount>\\d+)\\s*";
    public static String increaseMoneyAbbr = "\\s*increase -m (?<amount>\\d+)\\s*";
    public static String[] selectHandForce = {
            "\\s*select --hand \\s*(?<cardName>.*)\\s* --force\\s*",
            "\\s*select --force \\s*(?<cardName>.*)\\s* --hand\\s*"
    };
    public static String[] selectHandForceAbbr = {
            "\\s*select -h \\s*(?<cardName>.*)\\s* -f\\s*",
            "\\s*select -f \\s*(?<cardName>.*)\\s* -h\\s*"
    };
    public static String increaseLP = "\\s*increase --LP (?<amount>\\d+)\\s*";
    public static String increaseLPAbbr = "\\s*increase -LP (?<amount>\\d+)\\s*";
    public static String setWinner = "\\s*duel set-winner \\s*(?<nickname>.*)\\s*";

}

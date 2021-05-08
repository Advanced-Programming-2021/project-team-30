package model.regex;

public class DebugRegex {
    public static String increaseMoney = "\\s*increase --money (?<amount>\\d+)\\s*";
    public static String increaseMoneyAbbr = "\\s*increase -m (?<amount>\\d+)\\s*";
    public static String[] selectHandForce = {
            "select --hand (?<cardName>.*) --force",
            "select --force (?<cardName>.*) --hand"
    };
    public static String[] selectHandForceAbbr = {
            "select -h (?<cardName>.*) -f",
            "select -f (?<cardName>.*) -h"
    };
    public static String increaseLP = "\\s*increase --LP (?<amount>\\d+)\\s*";
    public static String increaseLPAbbr = "\\s*increase -LP (?<amount>\\d+)\\s*";
    public static String setWinner = "duel set-winner (?<nickname>.*)";

}

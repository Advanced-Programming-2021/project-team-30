package model.regex;

public class ShopMenuRegex {
    public static String cardShow = "\\s*card show \\s*(?<cardName>.*)\\s*";
    public static String buyCard = "\\s*shop buy \\s*(?<cardName>.*)\\s*";
    public static String showAllCards = "\\s*shop show --all\\s*";
    public static String showAllCardsAbbr = "\\s*shop show -a\\s*";


}

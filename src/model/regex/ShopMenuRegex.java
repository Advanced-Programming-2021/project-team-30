package model.regex;

public class ShopMenuRegex {
    public static String cardShow = "card show (?<cardName>.*)";
    public static String buyCard = "shop buy (?<cardName>.*)";
    public static String showAllCards = "\\s*shop show --all\\s*";
    public static String showAllCardsAbbr = "\\s*shop show -a\\s*";


}

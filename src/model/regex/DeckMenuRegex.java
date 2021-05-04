package model.regex;

public class DeckMenuRegex {
    public static String cardShow = "\\s*card show \\s*(?<cardName>.*)\\s*";
    public static String createDeck = "\\s*deck create \\s*(?<deckName>.*)\\s*";
    public static String deleteDeck = "\\s*deck delete \\s*(?<deckName>.*)\\s*";
    public static String setActiveDeck = "\\s*deck set-activate \\s*(?<deckName>.*)\\s*";
    public static String[] addCardMain = {
            "\\s*deck add-card --card \\s*(?<cardName>.*)\\s* --deck \\s*(?<deckName>.*)\\s*",
            "\\s*deck add-card --deck \\s*(?<deckName>.*)\\s* --card \\s*(?<cardName>.*)\\s*"
    };
    public static String[] addCardMainAbbr = {
            "\\s*deck add-card -c \\s*(?<cardName>.*)\\s* -d \\s*(?<deckName>.*)\\s*",
            "\\s*deck add-card -d \\s*(?<deckName>.*)\\s* -c \\s*(?<cardName>.*)\\s*"
    };
    public static String[] addCardSide = {
            "\\s*deck add-card --card \\s*(?<cardName>.*)\\s* --deck \\s*(?<deckName>.*)\\s* --side\\s*",
            "\\s*deck add-card --deck \\s*(?<deckName>.*)\\s* --card \\s*(?<cardName>.*)\\s* --side\\s*",
            "\\s*deck add-card --card \\s*(?<cardName>.*)\\s* --side --deck \\s*(?<deckName>.*)\\s*",
            "\\s*deck add-card --deck \\s*(?<deckName>.*)\\s* --side --card \\s*(?<cardName>.*)\\s*",
            "\\s*deck add-card --side --card \\s*(?<cardName>.*)\\s* --deck \\s*(?<deckName>.*)\\s*",
            "\\s*deck add-card --side --deck \\s*(?<deckName>.*)\\s* --card \\s*(?<cardName>.*)\\s*"
    };
    public static String[] addCardSideAbbr = {
            "\\s*deck add-card -c \\s*(?<cardName>.*)\\s* -d \\s*(?<deckName>.*)\\s* -s\\s*",
            "\\s*deck add-card -d \\s*(?<deckName>.*)\\s* -c \\s*(?<cardName>.*)\\s* -s\\s*",
            "\\s*deck add-card -c \\s*(?<cardName>.*)\\s* -s -d \\s*(?<deckName>.*)\\s*",
            "\\s*deck add-card -d \\s*(?<deckName>.*)\\s* -s -c \\s*(?<cardName>.*)\\s*",
            "\\s*deck add-card -s -c \\s*(?<cardName>.*)\\s* -d \\s*(?<deckName>.*)\\s*",
            "\\s*deck add-card -s -d \\s*(?<deckName>.*)\\s* -c \\s*(?<cardName>.*)\\s*"
    };
    public static String[] removeCardMain = {
            "\\s*deck rm-card --card \\s*(?<cardName>.*)\\s* --deck \\s*(?<deckName>.*)\\s*",
            "\\s*deck rm-card --deck \\s*(?<deckName>.*)\\s* --card \\s*(?<cardName>.*)\\s*"
    };
    public static String[] removeCardMainAbbr = {
            "\\s*deck rm-card -c \\s*(?<cardName>.*)\\s* -d \\s*(?<deckName>.*)\\s*",
            "\\s*deck rm-card -d \\s*(?<deckName>.*)\\s* -c \\s*(?<cardName>.*)\\s*"
    };
    public static String[] removeCardSide = {
            "\\s*deck rm-card --card \\s*(?<cardName>.*)\\s* --deck \\s*(?<deckName>.*)\\s* --side\\s*",
            "\\s*deck rm-card --deck \\s*(?<deckName>.*)\\s* --card \\s*(?<cardName>.*)\\s* --side\\s*",
            "\\s*deck rm-card --card \\s*(?<cardName>.*)\\s* --side --deck \\s*(?<deckName>.*)\\s*",
            "\\s*deck rm-card --deck \\s*(?<deckName>.*)\\s* --side --card \\s*(?<cardName>.*)\\s*",
            "\\s*deck rm-card --side --card \\s*(?<cardName>.*)\\s* --deck \\s*(?<deckName>.*)\\s*",
            "\\s*deck rm-card --side --deck \\s*(?<deckName>.*)\\s* --card \\s*(?<cardName>.*)\\s*"
    };
    public static String[] removeCardSideAbbr = {
            "\\s*deck rm-card -c \\s*(?<cardName>.*)\\s* -d \\s*(?<deckName>.*)\\s* -s\\s*",
            "\\s*deck rm-card -d \\s*(?<deckName>.*)\\s* -c \\s*(?<cardName>.*)\\s* -s\\s*",
            "\\s*deck rm-card -c \\s*(?<cardName>.*)\\s* -s -d \\s*(?<deckName>.*)\\s*",
            "\\s*deck rm-card -d \\s*(?<deckName>.*)\\s* -s -c \\s*(?<cardName>.*)\\s*",
            "\\s*deck rm-card -s -c \\s*(?<cardName>.*)\\s* -d \\s*(?<deckName>.*)\\s*",
            "\\s*deck rm-card -s -d \\s*(?<deckName>.*)\\s* -c \\s*(?<cardName>.*)\\s*"
    };
    public static String showAllDecks = "\\s*deck show --all\\s*";
    public static String showAllDecksAbbr = "\\s*deck show -a\\s*";
    public static String showMainDeck = "\\s*deck show --deck-name \\s*(?<deckName>.*)\\s*";
    public static String showMainDeckAbbr = "\\s*deck show -d \\s*(?<deckName>.*)\\s*";
    public static String[] showSideDeck = {
            "\\s*deck show --deck-name \\s*(?<deckName>.*) --side\\s*",
            "\\s*deck show --side --deck-name \\s*(?<deckName>.*)\\s*"
    };
    public static String[] showSideDeckAbbr = {
            "\\s*deck show -d \\s*(?<deckName>.*) -s\\s*",
            "\\s*deck show -s -d \\s*(?<deckName>.*)\\s*"
    };
    public static String showAllCards = "\\s*deck show --cards\\s*";
    public static String showAllCardsAbbr = "\\s*deck show -c\\s*";


}

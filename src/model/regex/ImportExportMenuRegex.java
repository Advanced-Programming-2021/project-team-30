package model.regex;

public class ImportExportMenuRegex {
    public static String importCard = "\\s*import card (?<cardName>.*)\\s*";
    public static String exportCard = "\\s*export card (?<cardName>.*)\\s*";
}

package model.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static Matcher getCommandMatcher(String input, String regex){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
    public static Matcher getCommandMatcherRegexes(String input, String[] regexes){
        for (String regex : regexes) {
            if (input.matches(regex)) {
                Pattern pattern = Pattern.compile(regex);
                return pattern.matcher(input);
            }
        }
        return null;
    }
    public static String returnMenuName(String command){
        Matcher matcher = getCommandMatcher(command, MenuRegex.enterMenu);
        if (matcher.find())
            return matcher.group(1);
        return "";
    }
}

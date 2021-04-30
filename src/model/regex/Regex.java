package model.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static Matcher getCommandMatcher(String input, String regex){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
}

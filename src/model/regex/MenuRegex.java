package model.regex;

public class MenuRegex {
    public static String enterMenu = "\\s*menu enter (\\S+)\\s*";
    public static String exitMenu = "\\s*menu exit\\s*";
    public static String showCurrent = "\\s*menu show-current\\s*";
    public static boolean isNotNavigationCommand(String command){
        return !(command.matches(enterMenu) || command.matches(exitMenu) || command.matches(showCurrent));
    }
}

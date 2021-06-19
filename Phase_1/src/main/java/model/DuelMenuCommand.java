package model;

import java.util.regex.Matcher;

public class DuelMenuCommand {
    public static Command commandName;
    public static Matcher matcher;
    public static boolean isSet;

    public static void setParams(Command commandName, Matcher matcher){
        DuelMenuCommand.commandName = commandName;
        DuelMenuCommand.matcher = matcher;
        isSet = true;
    }
}

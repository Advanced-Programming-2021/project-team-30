package model.menu;

import model.regex.ImportExportMenuRegex;
import model.regex.Regex;
import model.response.MenuResponse;
import view.Main;

import java.util.regex.Matcher;

public class ImportExportMenu {
    public void write(Matcher matcher){
        if (matcher.find()){
            String cardName = matcher.group("cardName");
        }

    }
    public void read(Matcher matcher){
        if (matcher.find()){
            String cardName = matcher.group("cardName");
        }

    }
    public void run(String command){
        if (Regex.getCommandMatcher(command, ImportExportMenuRegex.importCard).find())
            read(Regex.getCommandMatcher(command, ImportExportMenuRegex.importCard));
        else if (Regex.getCommandMatcher(command, ImportExportMenuRegex.exportCard).find())
            write(Regex.getCommandMatcher(command, ImportExportMenuRegex.exportCard));
        else
            Main.outputToUser(MenuResponse.invalidCommand);

    }

}

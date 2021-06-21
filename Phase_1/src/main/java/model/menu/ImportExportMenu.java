package model.menu;

import com.google.gson.Gson;
import model.Initializer;
import model.cards.Card;
import model.regex.ImportExportMenuRegex;
import model.regex.MenuRegex;
import model.regex.Regex;
import model.response.MenuResponse;
import view.Main;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ImportExportMenu {
    public void write(Matcher matcher){
        if (matcher.find()){
            String cardName = matcher.group("cardName");
            Card monsterCard = Initializer.monsterCardToBuild(cardName);
            Card spellTrapCard = Initializer.spellTrapCardToBuild(cardName);
            Card card = Initializer.cardToBuild(cardName);
            String path = "";
            if (monsterCard != null){
                path = "src/main/resources/cards/Monster/" + cardName + ".json";
            } else if (spellTrapCard != null){
                path = "src/main/resources/cards/SpellTrap/" + cardName + ".json";
            } else {
                Main.outputToUser("Card with this name does not exist!");
            }
            try {
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(new Gson().toJson(card));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void read(Matcher matcher){
        if (matcher.find()){
            String cardName = matcher.group("cardName");
            Card monsterCard = Initializer.monsterCardToBuild(cardName);
            Card spellTrapCard = Initializer.spellTrapCardToBuild(cardName);
            String path = "";
            if (monsterCard != null){
                path = "src/main/resources/cards/Monster/" + cardName + ".json";
            } else if (spellTrapCard != null){
                path = "src/main/resources/cards/SpellTrap/" + cardName + ".json";
            } else {
                Main.outputToUser("Card with this name does not exist!");
            }
            try {
                String json = new String(Files.readAllBytes(Paths.get(path)));
                Card card = new Gson().fromJson(json, Card.class);
                MainMenu.getCurrentUser().addCard(card);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
    public void run(String command){
        if (Regex.getCommandMatcher(command, ImportExportMenuRegex.importCard).find())
            read(Regex.getCommandMatcher(command, ImportExportMenuRegex.importCard));
        else if (Regex.getCommandMatcher(command, ImportExportMenuRegex.exportCard).find())
            write(Regex.getCommandMatcher(command, ImportExportMenuRegex.exportCard));
        else if (MenuRegex.isNotNavigationCommand(command))
            Main.outputToUser(MenuResponse.invalidCommand);

    }

}

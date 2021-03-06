package yugioh.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import yugioh.model.Deck;
import yugioh.model.DeckForDecksMenu;
import yugioh.view.*;

import java.util.ArrayList;

public class DeckMenuController {
    public TableView<DeckForDecksMenu> table;
    public Label activeDeckLabel;
    public static Stage deckCardsStage;
    static {
        deckCardsStage = new Stage();
        deckCardsStage.setWidth(830);
        deckCardsStage.setHeight(800);
        deckCardsStage.setX(650);
        deckCardsStage.setY(5);
    }

    public static ArrayList<DeckForDecksMenu> returnDecks(ArrayList<Deck> decks){
        ArrayList<DeckForDecksMenu> deckForDecksMenus = new ArrayList<>();
        for (Deck deck : decks) {
            deckForDecksMenus.add(new DeckForDecksMenu(deck.getName(), deck.getAllCards().size()));
        }
        return deckForDecksMenus;
    }

    public void initialize(){
        TableColumn<DeckForDecksMenu, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<DeckForDecksMenu, Integer> numberOfCardsCol = new TableColumn<>("Number of Cards");
        numberOfCardsCol.setCellValueFactory(new PropertyValueFactory<>("cardsNumber"));
        nameCol.setPrefWidth(400.0);
        numberOfCardsCol.setPrefWidth(155.0);
        table.getColumns().add(nameCol);
        table.getColumns().add(numberOfCardsCol);
        table.getItems().addAll(returnDecks(MainMenuController.currentUser.getDecks()));
        table.setRowFactory(tableView -> {
            TableRow<DeckForDecksMenu> row = new TableRow<>();
            row.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    try {
                        deckCardsStage.close();
                        ShowDeckCardsController.currentDeck = MainMenuController.currentUser.getPlayerDeckByName(row.getItem().getName());
                        new ShowDeckCardsView().start(deckCardsStage);
                    } catch (NullPointerException nullPointerException){
                        deckCardsStage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
        table.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                deckCardsStage.close();
            }
        });
    }

    public void addNewDeck(MouseEvent mouseEvent) {
        TextInputDialog td = new TextInputDialog("Deck name");
        td.setX(600);
        td.setY(300);
        td.setTitle("Create New Deck");
        td.setHeaderText("Enter Deck Name");
        td.showAndWait();
        String deckName = td.getResult();
        if (MainMenuController.currentUser.getPlayerDeckByName(deckName) != null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setX(550);
            alert.setY(300);
            alert.setHeaderText("Add New Deck failed!");
            alert.setContentText("Deck with this name already exists!");
            alert.show();
        } else {
            MainMenuController.currentUser.addToDecks(new Deck(deckName, MainMenuController.currentUser));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setX(550);
            alert.setY(300);
            alert.setHeaderText("Add New Deck Successful!");
            alert.setContentText("Deck created successfully!");
            alert.show();
            table.getItems().add(new DeckForDecksMenu(td.getResult(), 0));
        }
    }

    public void removeDeck(MouseEvent mouseEvent) {
        try {
            String deckName = table.getSelectionModel().getSelectedItem().getName();
            Deck deck = MainMenuController.currentUser.getPlayerDeckByName(deckName);
            MainMenuController.currentUser.removeFromDecks(deck);
            table.getItems().remove(table.getSelectionModel().getSelectedItem());
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setX(550);
            alert.setY(300);
            alert.setHeaderText("An Error Occurred!");
            alert.setContentText("Please select a deck!");
            alert.show();
        }
    }

    public void editDeck(MouseEvent mouseEvent) throws Exception {
        try {
            String deckName = table.getSelectionModel().getSelectedItem().getName();
            EditDeckController.currentDeck = MainMenuController.currentUser.getPlayerDeckByName(deckName);
            new EditDeckView().start(LoginMenuView.stage);
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setX(550);
            alert.setY(300);
            alert.setHeaderText("An Error Occurred!");
            alert.setContentText("Please select a deck!");
            alert.show();
        }
    }

    public void back(ActionEvent actionEvent) throws Exception {
        new MainMenuView().start(LoginMenuView.stage);
    }

    public void setActive(MouseEvent mouseEvent) {
        try {
            String deckName = table.getSelectionModel().getSelectedItem().getName();
            MainMenuController.currentUser.setActiveDeck(MainMenuController.currentUser.getPlayerDeckByName(deckName));
            activeDeckLabel.setText("Active Deck : " + deckName);
        } catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setX(550);
            alert.setY(300);
            alert.setHeaderText("An Error Occurred!");
            alert.setContentText("Please select a deck!");
            alert.show();
        }
    }
}

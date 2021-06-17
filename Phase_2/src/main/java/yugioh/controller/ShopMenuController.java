package yugioh.controller;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import yugioh.model.Card;
import yugioh.model.CardInitializer;

import java.io.File;
import java.util.Random;

public class ShopMenuController {

    public GridPane gridPane;
    public GridPane gridPane2;
    public ImageView cardImage;
    public Label userMoneyLabel;
    public Label cardNameLabel;
    public Label cardNumberLabel;
    public Label cardPriceLabel;
//    public void setCardImages(GridPane cardGridPane, File folder, ){
//
//    }

    public void initialize(){
        userMoneyLabel.setText("Money : " + MainMenuController.currentUser.getMoney());
        File monstersFolder = new File("src/main/resources/yugioh/assets/cards/Monsters");
        File[] monsterFiles = monstersFolder.listFiles();
        assert monsterFiles != null;
        ImageView[][] imageViews = new ImageView[4][12];
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                if (counter < monsterFiles.length) {
                    String path = "file:\\" + monsterFiles[counter].getAbsolutePath();
                    imageViews[i][j] = new ImageView(new Image(path));
                    imageViews[i][j].setFitWidth(60);
                    imageViews[i][j].setFitHeight(80);
                    counter++;
                    gridPane.add(imageViews[i][j], j, i);
                }
            }
        }
        File spellTrapFolder = new File("src/main/resources/yugioh/assets/cards/SpellTrap");
        File[] spellTrapFiles = spellTrapFolder.listFiles();
        assert spellTrapFiles != null;
        ImageView[][] imageViews2 = new ImageView[4][12];
        gridPane2.setHgap(5);
        gridPane2.setVgap(5);
        counter = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                if (counter < spellTrapFiles.length) {
                    String path = "file:\\" + spellTrapFiles[counter].getAbsolutePath();
                    imageViews2[i][j] = new ImageView(new Image(path));
                    imageViews2[i][j].setFitWidth(60);
                    imageViews2[i][j].setFitHeight(80);
                    counter++;
                    gridPane2.add(imageViews2[i][j], j, i);
                }
            }
        }
        GridPane[] gridPanes = new GridPane[]{gridPane, gridPane2};
        for (GridPane pane : gridPanes) {
            for (Node child : pane.getChildren()) {
                ImageView imageView = (ImageView) child;
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        cardImage.setImage(imageView.getImage());
                        String name = cardImage.getImage().getUrl();
                        File file = new File(name);
                        String cardName = file.getName().replace(".jpg", "");
                        cardNameLabel.setLayoutX(190 - (4 * cardName.length()));
                        cardNameLabel.setText("Card name : " + cardName);
                        Card card = CardInitializer.cardToBuild(cardName);
                        if (card != null) {
                            cardPriceLabel.setText("Price : " + card.getPrice());
                            cardNumberLabel.setText("Number of card : " + returnNumberOfUserCard(cardName));
                        }
                    }
                });
            }
        }
    }
    public int returnNumberOfUserCard(String cardName){
        int count = 0;
        for (Card card : MainMenuController.currentUser.getCards()) {
            if (card.getName().equals(cardName))
                count++;
        }
        return count;
    }

    public void buyCard(MouseEvent mouseEvent) {
        String cardName = cardNameLabel.getText().replace("Card name : ", "");
        Card card = CardInitializer.cardToBuild(cardName);
        assert card != null;
        if (card.getPrice() > MainMenuController.currentUser.getMoney()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Buy Card Failed!");
            alert.setContentText("You don't have enough money!");
            alert.show();
        } else {
            MainMenuController.currentUser.setMoney(MainMenuController.currentUser.getMoney() - card.getPrice());
            MainMenuController.currentUser.addCard(card);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Buy Card Successful!");
            alert.setContentText("Card added successfully!");
            alert.show();
            cardNumberLabel.setText("Number of card : " + returnNumberOfUserCard(cardName));
            userMoneyLabel.setText("Money : " + MainMenuController.currentUser.getMoney());
        }

    }
}

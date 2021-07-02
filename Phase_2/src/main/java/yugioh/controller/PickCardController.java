package yugioh.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import yugioh.model.cards.Card;
import yugioh.model.CardInitializer;
import yugioh.view.EditDeckView;
import yugioh.view.LoginMenuView;

import java.io.File;
import java.util.ArrayList;

public class PickCardController {
    public GridPane gridPane;
    public ImageView cardImage;
    public Label cardNameLabel;
    public static ArrayList<Card> deck;
    public static boolean isMainDeck;
    public void setCardImages(GridPane cardGridPane, ArrayList<Card> cards){
        ImageView[][] imageViews = new ImageView[8][15];
        cardGridPane.setHgap(5);
        cardGridPane.setVgap(5);
        int counter = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 15; j++) {
                if (counter < cards.size()) {
                    String path = EditDeckController.cardNameToImage(cards.get(counter).getName());
                    imageViews[i][j] = new ImageView(new Image(path));
                    imageViews[i][j].setFitWidth(60);
                    imageViews[i][j].setFitHeight(80);
                    counter++;
                    cardGridPane.add(imageViews[i][j], j, i);
                }
            }
        }

    }
    public void initialize(){
        setCardImages(gridPane, MainMenuController.currentUser.getCards());
        for (Node child : gridPane.getChildren()) {
            ImageView imageView = (ImageView) child;
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    cardImage.setImage(imageView.getImage());
                    String name = cardImage.getImage().getUrl();
                    File file = new File(name);
                    String cardName = file.getName().replace(".jpg", "");
                    cardNameLabel.setLayoutX(180 - (4 * cardName.length()));
                    cardNameLabel.setText("Card name : " + cardName);
                    Card card = CardInitializer.cardToBuild(cardName);
                }
            });
        }
    }

    public void chooseCard(MouseEvent mouseEvent) throws Exception {
        String cardName = cardNameLabel.getText().replace("Card name : ", "");
        Card card = CardInitializer.cardToBuild(cardName);
        assert card != null;
        if (deck != null){
            if (EditDeckController.currentDeck.returnCardCountDeck(cardName) == 3){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Add Card Failed!");
                alert.setContentText("There are already three cards of this kind in deck!");
                alert.show();
            } else {
                if (isMainDeck) {
                    EditDeckController.currentDeck.addCardToMainDeck(card);
                } else {
                    EditDeckController.currentDeck.addCardToSideDeck(card);
                }
            }
        }
        new EditDeckView().start(LoginMenuView.stage);

    }
}

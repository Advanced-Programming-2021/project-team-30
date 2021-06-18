package yugioh.view;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import yugioh.controller.MainMenuController;
import yugioh.model.Player;
import yugioh.model.PlayerForScoreBoard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreBoardView extends Application {
    public static int userRank = -1;
    public static Stage stage;
    public TableView<PlayerForScoreBoard> table = new TableView<>();
    public Label label = new Label();
    public Button button = new Button();
    public ArrayList<PlayerForScoreBoard> returnBestPlayers(){
        ArrayList<Player> sortedUsers = new ArrayList<>(Player.getPlayers());
        ArrayList<PlayerForScoreBoard> sortedPlayers = new ArrayList<>();
        Comparator<Player> playerComparator = Comparator.comparing(Player::getScore, Comparator.reverseOrder()).thenComparing(Player::getNickname);
        List<Player> sortedAccounts = sortedUsers.stream().sorted(playerComparator).collect(Collectors.toList());
        int rank = 1;
        int counter = 1;
        for (int i = 0; i < sortedAccounts.size(); i++) {
            Player sortedAccount = sortedAccounts.get(i);
            sortedPlayers.add(new PlayerForScoreBoard(rank, sortedAccount.getNickname(), sortedAccount.getScore()));
            if (i < sortedAccounts.size() - 1) {
                if (sortedAccount.getScore() != sortedAccounts.get(i + 1).getScore()) {
                    rank += counter;
                    counter = 1;
                }
                else{
                    counter++;
                }
            }
        }
        ArrayList<PlayerForScoreBoard> topTwentyBestPlayers = new ArrayList<>();
        if (sortedPlayers.size() > 20){
            for (int i = 0; i < 20; i++) {
                topTwentyBestPlayers.add(sortedPlayers.get(i));
            }
        } else {
            topTwentyBestPlayers.addAll(sortedPlayers);
        }
        return topTwentyBestPlayers;
    }
    public void labelAndButtonInit(){
        label.setLayoutX(660);
        label.setLayoutY(55.0);
        label.setText("Scoreboard");
        label.setFont(new Font(50));
        button.setLayoutX(690);
        button.setLayoutY(610.0);
        button.setPrefWidth(200);
        button.setMnemonicParsing(false);
        button.setText("Back");
        button.setFont(new Font(40));
        button.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                try {
                    new MainMenuView().start(LoginMenuView.stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Scene returnScoreboardScene(){
        Scene scene = new Scene(new Group());
        table.setEditable(true);
        table.setLayoutX(450);
        table.setLayoutY(140.0);
        table.setPrefHeight(450.0);
        table.setPrefWidth(700.0);
        TableColumn<PlayerForScoreBoard, Integer> rateCol = new TableColumn<>("Rate");
        rateCol.setCellValueFactory(new PropertyValueFactory<>("rate"));
        TableColumn<PlayerForScoreBoard, String> usernameCol = new TableColumn<>("Nickname");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("nickname"));
        TableColumn<PlayerForScoreBoard, Integer> scoreCol = new TableColumn<>("Score");
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        rateCol.setPrefWidth(150.0);
        usernameCol.setPrefWidth(400.0);
        scoreCol.setPrefWidth(150.0);
        table.getColumns().add(rateCol);
        table.getColumns().add(usernameCol);
        table.getColumns().add(scoreCol);
        int currentPlayerIndex = -1;
        ArrayList<PlayerForScoreBoard> playerForScoreBoards = returnBestPlayers();
        for (int i = 0; i < playerForScoreBoards.size(); i++) {
            table.getItems().add(playerForScoreBoards.get(i));
            if (playerForScoreBoards.get(i).getNickname().equals(MainMenuController.currentUser.getNickname()))
                currentPlayerIndex = i;
        }
        if (currentPlayerIndex != -1) {
            table.getSelectionModel().select(currentPlayerIndex);
            userRank = currentPlayerIndex;
        }
        labelAndButtonInit();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(900);
        anchorPane.setPrefWidth(1590);
        anchorPane.getChildren().add(table);
        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(button);
        anchorPane.setStyle("-fx-background-color: #1ed4a0");
        ((Group) scene.getRoot()).getChildren().addAll(anchorPane);
        return scene;
    }

    @Override
    public void start(Stage stage) throws Exception {
        ScoreBoardView.stage = stage;
        stage.setTitle("Scoreboard");
        stage.setMaximized(true);
        stage.setScene(returnScoreboardScene());
        table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                table.getSelectionModel().clearAndSelect(userRank);
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

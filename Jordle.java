import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import static javafx.scene.paint.Color.GREEN;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;

/**
 * This class creates a JavaFX program that plays Jordle.
 * @author Mashu Takeda mtakeda9
 * @version 1
 */
public class Jordle extends Application {
    private final int[] rowNum = {0};
    private final int[] columnNum = {0};
    private String character;
    @Override
    public void start(Stage primaryStage) {
        final String[] wordList = {Words.list.
            get((int) Math.round(Math.random() * Words.list.size())).toUpperCase()};
        VBox root = new VBox();
        Label label = new Label("Jordle");
        label.setFont(new Font("Times New Roman", 40));
        label.setPadding(new Insets(10, 10, 10, 10));
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Color.color(0.3, 0.3, 0.8));
        label.setOnMouseEntered((MouseEvent e) -> {
            label.setScaleX(1.5);
            label.setScaleY(1.5);
        });
        label.setOnMouseExited((MouseEvent e) -> {
            label.setScaleX(1);
            label.setScaleY(1);
        });
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: white");
        Rectangle[][] rectangles = new Rectangle[6][5];
        Stop[] stops = new Stop[]{
            new Stop(0.0, Color.web("#b19cd9")),
            new Stop(0.2, Color.WHITE),
            new Stop(0.5, Color.WHITE),
        };
        RadialGradient gradient = new RadialGradient(0, 0, 23, 23, 120, false, CycleMethod.NO_CYCLE, stops);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                rectangles[i][j] = new Rectangle(45, 45);
                rectangles[i][j].setFill(gradient);
            }
        }
        Label[][] labels = new Label[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                labels[i][j] = new Label();
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                grid.add(rectangles[i][j], j, i);
                grid.add(labels[i][j], j, i);
            }
        }
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setAlignment(Pos.BASELINE_CENTER);
        Label smallText = new Label("Try guessing a word!");
        Button restart = new Button("Restart");
        Button instruction = new Button("Instructions");
        hBox.getChildren().add(smallText);
        hBox.getChildren().add(restart);
        hBox.getChildren().add(instruction);
        root.getChildren().add(label);
        root.getChildren().add(grid);
        root.getChildren().add(hBox);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        restart.setOnMouseClicked(e -> {
            rowNum[0] = 0;
            columnNum[0] = 0;
            smallText.setText("Try guessing a word!");
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    rectangles[i][j].setFill(Color.PINK);
                    labels[i][j].setText("");
                }
            }
            wordList[0] = Words.list.get((int) Math.round(Math.random() * Words.list.size()));
        });
        instruction.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Pane pane = new Pane();
                Text text = new Text(15, 30, "Welcome to Jordle! Try guessing some words!\n"
                    + "Type in a 5-character word and press 'ctrl+enter'.\n"
                    + "  * Green  ->  correct letter in the correct place\n"
                    + "  * Yellow ->  correct letter in the wrong place\n"
                    + "  * Grey    ->  wrong letter in the wrong place\n"
                    + "You have 6 tries total to guess the word correctly. Good luck!");
                pane.getChildren().add(text);
                Scene sceneInstruction = new Scene(pane, 400, 150);
                Stage stage = new Stage();
                stage.setTitle("Instructions");
                stage.setScene(sceneInstruction);
                stage.show();
                pane.setStyle("-fx-background-color: linear-gradient(to right, white, pink)");
            }
        });
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
            case A: case B: case C: case D: case E: case F: case G: case H: case I: case J: case K: case L: case M:
            case N: case O: case P: case Q: case R: case S: case T: case U: case V: case W: case X: case Y: case Z:
                if (rowNum[0] < 5 && columnNum[0] <= 6) {
                    labels[columnNum[0]][rowNum[0]].setText(e.getText().toUpperCase());
                    labels[columnNum[0]][rowNum[0]].setFont(new Font("Times New Roman", 45));
                    labels[columnNum[0]][rowNum[0]].setPadding(new Insets(2, 2, 2, 8));
                    rowNum[0]++;
                }
                return;
            case BACK_SPACE:
                if (rowNum[0] > 0) {
                    labels[columnNum[0]][rowNum[0] - 1].setText("");
                    rowNum[0]--;
                }
                return;
            case ENTER:
                if (rowNum[0] == 5) {
                    int count = 0;
                    for (int i = 1; i < 6; i++) {
                        if (wordList[0].substring(5 - i, 6 - i).toUpperCase()
                            .equals(labels[columnNum[0]][rowNum[0] - i].getText())) {
                            rectangles[columnNum[0]][rowNum[0] - i].setFill(GREEN);
                            count++;
                        } else if (wordList[0].contains(labels[columnNum[0]][rowNum[0] - i].getText())) {
                            rectangles[columnNum[0]][rowNum[0] - i].setFill(Color.YELLOW);
                        } else {
                            rectangles[columnNum[0]][rowNum[0] - i].setFill(Color.GRAY);
                        }
                    }
                    columnNum[0]++;
                    rowNum[0] = 0;
                    if (count == 5) {
                        smallText.setText("Congratulations! You've guessed the word!");
                        wordList[0] = Words.list.get((int) Math.round(Math.random() * Words.list.size()));
                    } else if (columnNum[0] == 6) {
                        smallText.setText(String.format("Game over. The word was %s.", wordList[0].toLowerCase()));
                    }
                } else if (rowNum[0] <= 4) {
                    Pane pane = new Pane();
                    Scene alert = new Scene(pane, 300, 100);
                    Stage stage = new Stage();
                    stage.setTitle("Alert");
                    stage.setScene(alert);
                    Text text = new Text(50, 50, "Guess is invalid; Enter 5 characters!");
                    pane.getChildren().add(text);
                    stage.show();
                }
                return;
            default:
                break;
            }
        });
    }
}
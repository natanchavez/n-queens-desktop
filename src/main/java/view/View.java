package view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import static constants.Constants.NO_SOLUTIONS_TEXT;
import static constants.Constants.QUEEN_IMAGE;

/**
 * This class contains methods used to generate all the graphic elements used in the GUI.
 */
public class View {

    /**
     * This method generates the image of a chess board and puts some Queen pieces according to the selected solution
     * found in the N-Queens problem.
     *
     * @param boardPane           contains the GridPane where the board is going to be inserted.
     * @param chosenSolutionArray contains an array indicating the position of the queens on the board.
     */
    public void drawSolutionBoard(GridPane boardPane, int[] chosenSolutionArray) {
        GridPane solutionBoard = new GridPane();
        int boardSize = chosenSolutionArray.length;

        for (int currentRow = 0; currentRow < boardSize; currentRow++) {

            for (int currentColumn = 0; currentColumn < boardSize; currentColumn++) {
                String imageLocation = String.valueOf(getClass().getClassLoader().getResource(QUEEN_IMAGE));
                StackPane squarePane = new StackPane();
                String squareColor;

                if ((currentRow + currentColumn) % 2 == 0) {
                    squareColor = "gray";
                } else {
                    squareColor = "white";
                }

                if (currentColumn == chosenSolutionArray[currentRow]) {
                    squarePane.setStyle(
                            "-fx-background-color: " + squareColor + "; -fx-background-image: " +
                                    "url('" + imageLocation + "'); -fx-background-size: cover;");
                } else {
                    squarePane.setStyle("-fx-background-color: " + squareColor + ";");
                }
                solutionBoard.add(squarePane, currentColumn, currentRow);
            }
        }

        for (int i = 0; i < chosenSolutionArray.length; i++) {
            solutionBoard.getColumnConstraints().add(new ColumnConstraints(5,
                    Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            solutionBoard.getRowConstraints().add(new RowConstraints(5,
                    Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }

        boardPane.getChildren().clear();
        boardPane.add(solutionBoard, 0, 0);
    }

    /**
     * This method generates a label to be displayed when the selected number of queens doesn't have any solutions.
     *
     * @param boardPane contains the GridPane that is going to contain the generated message.
     */
    public void noSolutionsFound(GridPane boardPane) {
        Label noSolutionsLabel = new Label(NO_SOLUTIONS_TEXT);
        noSolutionsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 20; -fx-alignment: center");

        boardPane.add(noSolutionsLabel, 0, 0);
    }
}

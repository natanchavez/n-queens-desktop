package controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import model.BackTrackingModel;
import model.BruteForceModel;
import view.View;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//import view.View;

/**
 * This class contains methods used to call the functionality in the model and the view, it also has the proper
 * listeners used to handle certain interaction with the program.
 */
public class Controller implements Initializable {
    private final BruteForceModel bfModelObj;
    private final BackTrackingModel btModelObj;
    private final View viewObj;

    @FXML
    private GridPane boardPane;

    @FXML
    private ComboBox<String> algorithmComboBox;

    @FXML
    private ComboBox<Integer> nQueensComboBox;

    @FXML
    private ListView<Integer> solutionsListView;

    /**
     * The content of this constructor is executed when the object of type controller.Controller is instantiated, then
     * the model and view objects are instantiated.
     */
    public Controller() {
        this.viewObj = new View();
        bfModelObj = new BruteForceModel();
        btModelObj = new BackTrackingModel();
    }

    /**
     * This method is executed right after the GUI has been drawn, it sets the proper listeners of this program that are
     * used on the combo boxes and the list view.
     *
     * @param location  contains the URL location of the fxml file.
     * @param resources contains a variable from where the resources are stored.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        algorithmComboBox.valueProperty().addListener(this::algorithmComboBoxListener);
        nQueensComboBox.valueProperty().addListener(this::nQueensComboBoxListener);
        solutionsListView.getSelectionModel().selectedItemProperty().addListener(this::solutionsListViewListener);
    }

    /**
     * This method is called by a listener assigned to the combo box and registers the type of algorithm that was
     * selected by the user.
     *
     * @param observable      entity that wraps a value and allows to observe the value for changes.
     * @param oldValue        the value selected in the combo box before the user chooses a new one.
     * @param chosenAlgorithm the new value selected by the user in the combo box.
     */
    private void algorithmComboBoxListener(
            ObservableValue<? extends String> observable, String oldValue, String chosenAlgorithm) {

        clearBoardAndListView();
        nQueensComboBox.getItems().clear();

        if (chosenAlgorithm != null)
            populateNQueensComboBox(chosenAlgorithm);
    }

    /**
     * This method is called by a listener assigned to the combo box and registers the desired number of queens on the
     * board that was selected by the user.
     *
     * @param observable        entity that wraps a value and allows to observe the value for changes.
     * @param oldValue          the value selected in the combo box before the user chooses a new one.
     * @param chosenNumOfQueens the new value selected by the user in the combo box.
     */
    private void nQueensComboBoxListener(
            ObservableValue<? extends Integer> observable, Integer oldValue, Integer chosenNumOfQueens) {

        List<int[]> solutionsList;
        String chosenAlgorithm = algorithmComboBox.getSelectionModel().getSelectedItem();
        clearBoardAndListView();

        if (chosenNumOfQueens != null) {
            solutionsList = findSolutions(chosenAlgorithm, chosenNumOfQueens);

            if (solutionsList.size() > 0) {
                int numberOfSolutions = solutionsList.size();
                int[] firstSolutionArray = solutionsList.get(0);

                populateListView(numberOfSolutions);
                drawChosenSolution(firstSolutionArray);//so the user can see the very first solution always.
            } else {
                viewObj.noSolutionsFound(boardPane);
            }
        }
    }

    /**
     * This method is called by a listener assigned to the list view and registers the selected solution (if exists) in
     * order to graphically show it on a 2D generated board.
     *
     * @param observable          entity that wraps a value and allows to observe the value for changes.
     * @param oldValue            the value selected in the list view before the user chooses a new one.
     * @param chosenSolutionIndex the new value selected by the user in the list view.
     */
    private void solutionsListViewListener(
            ObservableValue<? extends Integer> observable, Integer oldValue, Integer chosenSolutionIndex) {
        boardPane.getChildren().clear(); //clean only the board pane and keep the list view.

        if (chosenSolutionIndex != null) {
            int[] chosenSolutionArray = new int[0];
            String chosenAlgorithm = algorithmComboBox.getSelectionModel().getSelectedItem();

            switch (chosenAlgorithm) {
                case "Brute Force":
                    chosenSolutionArray = bfModelObj.getSolutionsList().get(chosenSolutionIndex - 1);//-1
                    // because the index of the list view's first element is not zero but one.
                    break;
                case "Backtracking":
                    chosenSolutionArray = btModelObj.getSolutionsList().get(chosenSolutionIndex - 1);//-1
                    // because the index of the list view's first element is not zero but one.
                    break;
            }
            drawChosenSolution(chosenSolutionArray);
        }
    }

    /**
     * This method is called whenever the board pane and the list view must be cleared, this to avoid overlapping of
     * objects.
     */
    public void clearBoardAndListView() {
        boardPane.getChildren().clear();
        solutionsListView.getItems().clear();
    }

    /**
     * This method is called when an algorithm has been chosen, it populates the corresponding combo box with a valid
     * number of queens to select according to the algorithm.
     *
     * @param chosenAlgorithm contains the selected algorithm that is going to be used to find the solutions to the
     *                        N-Queens problem.
     */
    public void populateNQueensComboBox(String chosenAlgorithm) {
        int maxNumOfSolutions = 0;

        switch (chosenAlgorithm) {
            case "Brute Force":
                maxNumOfSolutions = 5;
                break;
            case "Backtracking":
                maxNumOfSolutions = 12;
                break;
        }

        for (int currentIndex = 1; currentIndex <= maxNumOfSolutions; currentIndex++) {
            nQueensComboBox.getItems().add(currentIndex);
        }
    }

    /**
     * This method is called in order to find all the possible solutions to the N-Queens problem, then a method in the
     * model is going to be called according to the selected algorithm.
     *
     * @param chosenAlgorithm   contains the selected algorithm that is going to be used to find the solutions to the
     *                          N-Queens problem.
     * @param chosenNumOfQueens contains the selected number of queens per board.
     */
    public List<int[]> findSolutions(String chosenAlgorithm, Integer chosenNumOfQueens) {
        List<int[]> solutionsList = new ArrayList<>();

        switch (chosenAlgorithm) {
            case "Brute Force":
                bfModelObj.bruteForceApproach(chosenNumOfQueens);
                solutionsList = bfModelObj.getSolutionsList();
                break;
            case "Backtracking":
                btModelObj.backtrackingApproach(chosenNumOfQueens);
                solutionsList = btModelObj.getSolutionsList();
                break;
        }
        return solutionsList;
    }

    /**
     * This method is called when a valid number of solutions were found, then the corresponding list view is going to
     * be populated with numbers so the user can choose which board is displayed.
     *
     * @param numberOfSolutions contains the number of valid boards found previously.
     */
    public void populateListView(int numberOfSolutions) {
        for (int currentIndex = 0; currentIndex < numberOfSolutions; currentIndex++) {
            solutionsListView.getItems().add(currentIndex + 1);
            //+1 so the user doesn't see the first element as zero but one.
        }
    }

    /**
     * This method is called when the user clicks on an entry in the list view, it calls the corresponding method in the
     * view to draw the selected solution so the user can see it.
     *
     * @param chosenSolutionArray contains an array with the position of the queens in the selected board.
     */
    public void drawChosenSolution(int[] chosenSolutionArray) {
        boardPane.getChildren().clear();
        viewObj.drawSolutionBoard(boardPane, chosenSolutionArray);
    }
}

import model.BackTrackingModel;
import model.BruteForceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class contains methods used to test the correct functionality of this program.
 */
class ModelTest {
    private BruteForceModel bruteForceModel;
    private BackTrackingModel backTrackingModel;
    private List<String[]> actualTempSolutionsList;


    @BeforeEach
    void setUp() {
        this.bruteForceModel = new BruteForceModel();
        this.backTrackingModel = new BackTrackingModel();
        actualTempSolutionsList = new ArrayList<>();
    }

    @Test
    void bruteForceApproach() {
        //Arrange
        int nQueensArgument = 5;
        int expectedSolutionsListSize = 10;

        //Act
        bruteForceModel.bruteForceApproach(nQueensArgument);
        int actualSolutionsListSize = bruteForceModel.getSolutionsList().size();

        //Assert
        assertEquals(expectedSolutionsListSize, actualSolutionsListSize,
                "The total numbers of solutions found using the brute force algorithm is wrong.");
    }

    @Test
    void generateAllCombinations() {
        //Arrange
        int nQueensArgument = 4;
        int expectedSolutionsListSize = 1820; //    (n^2 C n)

        //Act
        actualTempSolutionsList = bruteForceModel.generateAllCombinations(nQueensArgument, this.actualTempSolutionsList);
        int actualSolutionsListSize = actualTempSolutionsList.size();

        //Assert
        assertEquals(expectedSolutionsListSize, actualSolutionsListSize,
                "The size of the temporal solutions list is not correct.");
    }

    @Test
    void oneQueenPerRank() {
        //Arrange
        String[][] arrayOfBoards =
                {new String[]{"1111", "0000", "0000", "0000"}, new String[]{"0000", "1111", "0000", "0000"},
                new String[]{"0000", "0000", "1111", "0000"}, new String[]{"0000", "0000", "0000", "1111"},
                new String[]{"1000", "1000", "1000", "1000"}, new String[]{"0001", "0001", "0001", "0001"}};
        Collections.addAll(actualTempSolutionsList, arrayOfBoards);

        List<String[]> expectedSolutionsList = new ArrayList<>();
        String[][] arrayOfValidBoards = {new String[]{"1000", "1000", "1000", "1000"},
                new String[]{"0001", "0001", "0001", "0001"}};
        Collections.addAll(expectedSolutionsList, arrayOfValidBoards);


        //Act
        actualTempSolutionsList = bruteForceModel.oneQueenPerRank(actualTempSolutionsList);

        //Assert
        for (int currentIndex = 0; currentIndex < actualTempSolutionsList.size(); currentIndex++) {
            assertArrayEquals(expectedSolutionsList.get(currentIndex), actualTempSolutionsList.get(currentIndex),
                    "The actual solutions list is invalid, it must be only 1 queen per rank.");
        }
    }

    @Test
    void oneQueenPerColumnAndDiagonal() {
        //Arrange
        String[][] arrayOfBoards =
                {new String[]{"1111", "0000", "0000", "0000"}, new String[]{"0000", "1111", "0000", "0000"},
                new String[]{"0000", "0000", "1111", "0000"}, new String[]{"0000", "0000", "0000", "1111"},
                new String[]{"0100", "0001", "1000", "0010"}, new String[]{"0010", "1000", "0001", "0100"}};
        Collections.addAll(actualTempSolutionsList, arrayOfBoards);

        List<String[]> expectedSolutionsList = new ArrayList<>();
        String[][] arrayOfValidBoards = {new String[]{"0100", "0001", "1000", "0010"},
                new String[]{"0010", "1000", "0001", "0100"}};
        Collections.addAll(expectedSolutionsList, arrayOfValidBoards);


        //Act
        actualTempSolutionsList = bruteForceModel.oneQueenPerColumnAndDiagonal(actualTempSolutionsList);

        //Assert
        for (int currentIndex = 0; currentIndex < actualTempSolutionsList.size(); currentIndex++) {
            assertArrayEquals(expectedSolutionsList.get(currentIndex), actualTempSolutionsList.get(currentIndex),
                    "The actual solutions list is invalid, it must be only 1 queen per column and diagonal.");
        }
    }

    @Test
    void reformatTempSolutionList() {
        //Arrange
        String[][] arrayOfValidBoards =
                {new String[]{"0100", "0001", "1000", "0010"}, new String[]{"0010", "1000", "0001", "0100"}};
        Collections.addAll(actualTempSolutionsList, arrayOfValidBoards);

        int[][] arrayOfQueensPositions = {new int[]{1, 3, 0, 2}, new int[]{2, 0, 3, 1}};
        List<int[]> expectedSolutionsList = new ArrayList<>();
        Collections.addAll(expectedSolutionsList, arrayOfQueensPositions);

        //Act
        bruteForceModel.reformatTempSolutionList(actualTempSolutionsList);
        List<int[]> actualSolutionsList = bruteForceModel.getSolutionsList();

        //Assert
        for (int currentIndex = 0; currentIndex < actualTempSolutionsList.size(); currentIndex++) {
            assertArrayEquals(expectedSolutionsList.get(currentIndex), actualSolutionsList.get(currentIndex),
                    "The actual solutions list is invalid, it must be only 1 queen per column and diagonal.");
        }
    }

    @Test
    void backtrackingApproach() {
        //Arrange
        int nQueensArgument = 12;
        int expectedSolutionsListSize = 14200;

        //Act
        backTrackingModel.backtrackingApproach(nQueensArgument);
        int actualSolutionsListSize = backTrackingModel.getSolutionsList().size();

        //Assert
        assertEquals(expectedSolutionsListSize, actualSolutionsListSize,
                "The total numbers of solutions found using the backtracking algorithm is wrong.");
    }

    @Test
    void backtrackingAlgorithm() {
        //Arrange
        int nQueensArgument = 4;
        int[][] arrayOfQueensPositions = {new int[]{1, 3, 0, 2}, new int[]{2, 0, 3, 1}};
        List<int[]> expectedSolutionsList = new ArrayList<>();
        Collections.addAll(expectedSolutionsList, arrayOfQueensPositions);

        //Act
        backTrackingModel.backtrackingApproach(nQueensArgument);
        List<int[]> actualSolutionsList = backTrackingModel.getSolutionsList();

        //Assert
        for (int currentIndex = 0; currentIndex < actualTempSolutionsList.size(); currentIndex++) {
            assertArrayEquals(expectedSolutionsList.get(currentIndex), actualSolutionsList.get(currentIndex),
                    "The actual solutions list is invalid, it must be only 1 queen per row, column and diagonal.");
        }
    }
}
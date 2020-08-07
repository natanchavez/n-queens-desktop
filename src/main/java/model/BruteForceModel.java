package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods used to find all possible solutions for the N-Queens problem, using an algorithm that
 * relies purely on brute force.
 */
public class BruteForceModel {
    private List<int[]> solutionsList;

    /**
     * Gets the value of the solutions list variable.
     *
     * @return an array list type that contains the position of the queens in the board.
     */
    public List<int[]> getSolutionsList() {
        return solutionsList;
    }

    /**
     * Sets the value in the solutions list variable.
     *
     * @param solutionsList contains the solutions list containing all the valid boards already found.
     */
    public void setSolutionsList(List<int[]> solutionsList) {
        this.solutionsList = solutionsList;
    }

    /**
     * This method is VERY inefficient because it calculates all the possible combinations without any constraint, this
     * means that for a nxn board we are going to have 2^(n*n) generated candidates. Because of the previous statement
     * this program is only allowed to calculate up to 5 queens, so it doesn't take hours or days calculating all
     * possible solutions.
     *
     * @param chosenNumOfQueens contains the selected number of queens on the board.
     */
    public void bruteForceApproach(Integer chosenNumOfQueens) {
        List<String[]> tempSolutionsList = new ArrayList<>();

        tempSolutionsList = generateAllCombinations(chosenNumOfQueens, tempSolutionsList);
        tempSolutionsList = oneQueenPerRank(tempSolutionsList);
        tempSolutionsList = oneQueenPerColumnAndDiagonal(tempSolutionsList);
        reformatTempSolutionList(tempSolutionsList);
    }

    /**
     * This method is used to generate all possible boards without taking into account if they're valid or not, and for
     * every generated board saves only those with n queens without considering any other constrain.
     *
     * @param chosenNumOfQueens contains the selected number of queens in the board.
     * @param tempSolutionsList contains a temporal list with all the possible boards regardless of if they are valid or
     *                          not.
     * @return an array list of string containing the representation of the generated boards as ones and zeroes.
     */
    public List<String[]> generateAllCombinations(Integer chosenNumOfQueens, List<String[]> tempSolutionsList) {

        for (int i = 1; i <= Math.pow(2, chosenNumOfQueens * chosenNumOfQueens); i++) {

            String binaryString = Integer.toBinaryString(i);// 6 -> 110
            long queensPerBoard = binaryString.chars().filter(num -> num == '1').count();

            if (queensPerBoard != chosenNumOfQueens) continue;

            int leadingZeroes = (chosenNumOfQueens * chosenNumOfQueens);
            binaryString = String.format("%" + leadingZeroes + "s", binaryString).replace(" ", "0");//110 -> 0110

            String[] nonVerifiedSolution = binaryString.split("(?<=\\G.{" + chosenNumOfQueens + "})");//{[01], [10]}
            tempSolutionsList.add(nonVerifiedSolution);
        }

        return tempSolutionsList;
    }

    /**
     * This method is used to check and filter all generated boards in order to keep only those with one queen per
     * rank.
     *
     * @param tempSolutionsList contains a temporal list with all the possible boards regardless of if they are valid or
     *                          not.
     * @return an array list of string containing the representation of the generated boards as ones and zeroes.
     */
    public List<String[]> oneQueenPerRank(List<String[]> tempSolutionsList) {
        List<Integer> invalidBoardsList = new ArrayList<>();

        for (int currentBoard = 0; currentBoard < tempSolutionsList.size(); currentBoard++) {

            for (String currentRank : tempSolutionsList.get(currentBoard)) {
                long queensPerRank = currentRank.chars().filter(num -> num == '1').count();

                if (queensPerRank != 1) {
                    invalidBoardsList.add(currentBoard);
                    break;
                }
            }
        }

        for (int currentIndex = invalidBoardsList.size() - 1; currentIndex >= 0; currentIndex--) {
            int invalidBoardIndex = invalidBoardsList.get(currentIndex);
            tempSolutionsList.remove(invalidBoardIndex);
        }

        return tempSolutionsList;
    }

    /**
     * This method is used to check and filter all the generated boards in order to keep only those with one queen per
     * column and per diagonal.
     *
     * @param tempSolutionsList contains a temporal list with all the possible boards regardless of if they are valid or
     *                          not.
     * @return an array list of string containing the representation of the generated boards as ones and zeroes.
     */
    public List<String[]> oneQueenPerColumnAndDiagonal(List<String[]> tempSolutionsList) {
        List<Integer> invalidBoardsList = new ArrayList<>();

        for (int boardIndex = 0; boardIndex < tempSolutionsList.size(); boardIndex++) {
            String[] currentBoard = tempSolutionsList.get(boardIndex);

            for (int upperRank = 0; upperRank < currentBoard.length; upperRank++) {
                int queenPosition = currentBoard[upperRank].indexOf("1");

                for (int lowerRank = upperRank + 1; lowerRank < currentBoard.length; lowerRank++) {
                    int secondQueenPosition = currentBoard[lowerRank].indexOf("1");

                    if (queenPosition == secondQueenPosition ||
                            Math.abs(queenPosition - secondQueenPosition) == Math.abs(upperRank - lowerRank)) {
                        invalidBoardsList.add(boardIndex);
                        upperRank = currentBoard.length;
                        break;
                    }
                }
            }
        }

        for (int currentIndex = invalidBoardsList.size() - 1; currentIndex >= 0; currentIndex--) {
            int invalidBoardIndex = invalidBoardsList.get(currentIndex);
            tempSolutionsList.remove(invalidBoardIndex);
        }

        return tempSolutionsList;
    }

    /**
     * This method is used to give a proper format to the already obtained solutions, in other words, this method takes
     * the solutions obtained so far and keeps only the position of every queen per rank achieving with this more
     * clarity at the time of showing these solutions on the GUI.
     *
     * @param tempSolutionsList contains the temporal list with all the valid solutions.
     */
    public void reformatTempSolutionList(List<String[]> tempSolutionsList) {
        setSolutionsList(new ArrayList<>());
        if (tempSolutionsList.size() < 1) return;

        int chosenNumOfQueens = tempSolutionsList.get(0).length;
        int[] solutionArray;

        for (String[] currentBoard : tempSolutionsList) {
            solutionArray = new int[chosenNumOfQueens];

            for (int currentRank = 0; currentRank < solutionArray.length; currentRank++) {
                solutionArray[currentRank] = currentBoard[currentRank].indexOf('1');
            }

            saveValidSolution(solutionArray);
        }
    }

    /**
     * This method is used only when valid solutions have been found and after that they are stored in an array list
     * that can be accessed later on by the controller.
     *
     * @param solutionArray contains an array with the valid positions of the queens in a board.
     */
    public void saveValidSolution(int[] solutionArray) {
        int[] boardResult = new int[solutionArray.length];

        System.arraycopy(solutionArray, 0, boardResult, 0, solutionArray.length);
        getSolutionsList().add(boardResult);
    }
}

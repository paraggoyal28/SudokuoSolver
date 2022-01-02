package sudoku.computationlogic;

import sudoku.problemdomain.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class GameGenerator {

    public static  int[][] getNewGameGrid() {
        return unsolveGame(getSolvedGame());
    }

    private static int[][] unsolveGame(int[][] solvedGame) {
        Random random = new Random(System.currentTimeMillis());

        boolean solvable = false;
        int[][] solvableArray = new int[GRID_BOUNDARY][GRID_BOUNDARY];

        SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);

        int index = 0;

        while(index < 40) {
            int xCoordinate = random.nextInt(GRID_BOUNDARY);
            int yCoordinate = random.nextInt(GRID_BOUNDARY);

            if(solvableArray[xCoordinate][yCoordinate] != 0) {
                solvableArray[xCoordinate][yCoordinate] = 0;
                index++;
            }
        }

        return solvableArray;
    }

    private static int[][] getSolvedGame() {
        int[][] newGrid = new int[GRID_BOUNDARY][GRID_BOUNDARY];
        fillDiagonal(newGrid);
        fillRemaining(newGrid, 0, GRID_BOUNDARY/3);
        return newGrid;
    }

    private static boolean fillRemaining(int[][] newGrid, int row, int col) {
        if(col >= GRID_BOUNDARY && row < GRID_BOUNDARY - 1){
            row = row + 1;
            col = 0;
        }
        if(row >= GRID_BOUNDARY && col >= GRID_BOUNDARY) {
            return true;
        }
        if(row < GRID_BOUNDARY/3){
            if(col < GRID_BOUNDARY/3) {
                col = GRID_BOUNDARY/3;
            }
        } else if(row < (GRID_BOUNDARY - GRID_BOUNDARY/3)){
            if(col == (row/(GRID_BOUNDARY/3))*(GRID_BOUNDARY/3)) {
                col = col + GRID_BOUNDARY/3;
            }
        } else {
            if(col == (GRID_BOUNDARY - GRID_BOUNDARY/3)) {
                row = row + 1;
                col = 0;
                if(row >= GRID_BOUNDARY) {
                    return true;
                }
            }
        }

        for(int num = 1; num <=GRID_BOUNDARY; ++num){
            if(CheckIfSafe(newGrid, row, col, num)) {
                newGrid[row][col] = num;
                if (fillRemaining(newGrid, row, col + 1)) {
                    return true;
                }
                newGrid[row][col] = 0;
            }
        }
        return false;
    }

    private static boolean CheckIfSafe(int[][] newGrid, int row, int col, int num) {
        if(unUsedInRow(newGrid, row, num)
                && unUsedInCol(newGrid, col, num)
                && unUsedInBox(newGrid, row-row%(GRID_BOUNDARY/3),
                col-col%(GRID_BOUNDARY/3), num))
            return true;
        return false;
    }

    private static boolean unUsedInRow(int[][] newGrid, int row, int num) {
        for(int col = 0; col < GRID_BOUNDARY; ++col) {
            if(newGrid[row][col] == num){
                return false;
            }
        }
        return true;
    }

    private static boolean unUsedInCol(int[][] newGrid, int col, int num) {
        for(int row = 0;row < GRID_BOUNDARY; ++row) {
            if(newGrid[row][col] == num) {
                return false;
            }
        }
        return true;
    }

    private static void fillDiagonal(int[][] newGrid) {
        for(int xIndex = 0; xIndex < GRID_BOUNDARY; xIndex += GRID_BOUNDARY/3) {
            fillBox(newGrid, xIndex, xIndex);
        }
    }

    private static void fillBox(int[][] newGrid, int row, int col) {
        Random random = new Random(System.currentTimeMillis());
        int num;
        for(int xIndex = 0; xIndex < GRID_BOUNDARY/3; ++xIndex) {
            for(int yIndex = 0; yIndex < GRID_BOUNDARY/3; ++yIndex) {
                do {
                    num = random.nextInt(GRID_BOUNDARY)+1;
                } while(!unUsedInBox(newGrid, row, col, num));
                newGrid[row+xIndex][col+yIndex] = num;
            }
        }
    }

    private static boolean unUsedInBox(int[][] newGrid, int row, int col, int num) {
        for (int xIndex = 0; xIndex < GRID_BOUNDARY/3; xIndex++)
            for (int yIndex = 0; yIndex<GRID_BOUNDARY/3; yIndex++)
                if (newGrid[row+xIndex][col+yIndex]==num)
                    return false;
        return true;
    }


    private static void clearArray(int[][] newGrid) {
        for(int xIndex = 0; xIndex < GRID_BOUNDARY; ++xIndex) {
            for(int yIndex = 0;yIndex < GRID_BOUNDARY; ++yIndex) {
                newGrid[xIndex][yIndex] = 0;
            }
        }
    }
}

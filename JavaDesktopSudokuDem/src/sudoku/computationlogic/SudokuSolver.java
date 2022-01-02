package sudoku.computationlogic;

import sudoku.problemdomain.Coordinates;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDARY;

public class SudokuSolver {

    public static boolean puzzleIsSolvable(int[][] puzzle) {
        Coordinates[] emptyCells = typeWriterEnumerate(puzzle);

        int index = 0;
        int input = 1;

        while(index < 40) {
            Coordinates current = emptyCells[index];
            input = 1;

            while(input < 10) {
                puzzle[current.getX()][current.getY()] = input;

                if(GameLogic.sudokuIsInvalid(puzzle)) {
                    if(index == 0 && input == GRID_BOUNDARY) {
                        return false;
                    } else if(input == GRID_BOUNDARY) {
                        index--;
                    }
                    input++;
                }else {
                    index++;
                    if (index == 39) return true;
                    input = 1;
                }
            }
        }
        return false;
    }

    private static Coordinates[] typeWriterEnumerate(int[][] puzzle) {
        Coordinates[] emptyCells = new Coordinates[40];
        int iterator = 0;
        for(int x = 0; x < GRID_BOUNDARY; ++x){
            for(int y = 0; y < GRID_BOUNDARY; ++y) {
                if(puzzle[x][y] == 0) {
                    emptyCells[iterator] = new Coordinates(x, y);
                    if(iterator == 39) return emptyCells;
                    iterator++;
                }
            }
        }
        return emptyCells;
    }

}

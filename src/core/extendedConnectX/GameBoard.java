package core.extendedConnectX;

public class GameBoard extends AbsGameBoard implements IGameBoard {
    /**
     * @invariant [tokens = valid character]
     * @invariant [BoardPosition(s) must be valid]
     * @invariant 3 <= numColumns <= 100
     * @invariant 3 <= numRows <= 100
     * @invariant 3 <= numToWin <= numRows and numColumns and 25
     * @invariant [maintain valid sized gameboard with no spaces between tokens]
     *
     * Correspondence number_of_rows = numRows
     * Correspondence number_of_columns = numColumns
     * Correspondence self = myBoard[0...numRows-1][0...numColumns-1]
     * Correspondence number_to_win = numToWin
     */

    private Character[][] myBoard;

    private int numRows;
    private int numColumns;
    private int numToWin;

    public GameBoard(int rows, int columns, int winNum) {
        numRows = rows;
        numColumns = columns;
        numToWin = winNum;
        myBoard = new Character[numRows][numColumns];

        // Fills in the newly made GameBoard with spaces
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                myBoard[i][j] = ' ';
            }
        }
    }

    public void placeToken(char p, int c) {
        // Places token at the lowest possible space-filled index in the column chosen
        for (int i = 0; i < getNumRows(); i++) {
            if (myBoard[i][c] == ' ') {
                myBoard[i][c] = p;
                break;
            }
        }
    }

    public char whatsAtPos(BoardPosition pos) {
        return myBoard[pos.getRow()][pos.getColumn()];
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getNumToWin() {
        return numToWin;
    }

}

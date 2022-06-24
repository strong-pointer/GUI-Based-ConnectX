package core.extendedConnectX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoardMem extends AbsGameBoard implements IGameBoard {
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
     * Correspondence self = myBoard<Character, List<BoardPosition>>() [a map]
     * Correspondence number_to_win = numToWin
     */


    private Map<Character, List<BoardPosition>> myBoard;

    private int numRows;
    private int numColumns;
    private int numToWin;

    public GameBoardMem(int rows, int columns, int winNum) {
        numRows = rows;
        numColumns = columns;
        numToWin = winNum;
        myBoard = new HashMap<Character, List<BoardPosition>>();
    }

    public void placeToken(char p, int c) {
        // Places token at the lowest possible empty index in the column chosen
        for (int i = 0; i < getNumRows(); i++) {
            BoardPosition pos = new BoardPosition(i, c);
            List<BoardPosition> position =  new ArrayList<>();
            position.add(pos);

            // Checks for the next open row to slot in
            if (isPlayerAtPos(pos, ' ')) {
                List<BoardPosition> current = myBoard.get(p);
                if (current == null) {
                    myBoard.put(p, new ArrayList<BoardPosition>());
                }
                myBoard.get(p).add(pos);
                break;
            }
        }
    }

    public char whatsAtPos(BoardPosition pos) {
        for (Character player : myBoard.keySet()) {
            List<BoardPosition> position = myBoard.get(player);
            if (position.contains(pos)) {
                return player;
            }
        }
        // Gives back a space if unused space is there
        return ' ';
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

    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player) {
        // Overridden to compare BoardPositions to the player character
        return whatsAtPos(pos) == player;
    }
}

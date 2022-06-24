package core.extendedConnectX;

/**
 * GameBoard is abstractly a 2 dimensional array grid of characters, or a map of characters.
 * Indexing starts at 0 for the array implementation.
 *
 * Initialization ensures:
 *      GameBoard is empty
 *      and is numRows x numColumns
 *
 * Defines:     number_of_rows: Z
 *              number_of_columns: Z
 *              number_to_win: Z
 *              GameBoard is Abstract
 * Constraints: 3 <= number_of_rows <= 100
 *              3 <= number_of_columns <= 100
 *              3 <= number_to_win <= number_of_rows and number_of_columns and 25
 */
public interface IGameBoard {
    /**
     * @pre None
     * @return the number of rows
     * @post numRows = #numRows
     */
    // Returns the number of rows in the GameBoard
    public int getNumRows();

    /**
     * @pre None
     * @return the number of columns
     * @post numColumns = #numColumns
     */
    // Returns the number of columns in the GameBoard
    public int getNumColumns();

    /**
     * @pre None
     * @return the number of tokens to win
     * @post numToWin = #numToWin
     */
    // Returns the number of tokens in a row needed to win the game
    public int getNumToWin();

    /**
     * @pre 1 <= c <= numColumns
     * @param c number of the column to check
     * @return true or false depending on the column chosen being full
     * @post [checks if c = ' ']
     */
    // Tests for an empty column and returns true if it is
    default public boolean checkIfFree (int c) {
        BoardPosition pos = new BoardPosition(getNumRows()-1, c);
        if (whatsAtPos(pos) == ' ') {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @pre 1 <= c <= numColumns
     * @param c number of the column to check for win
     * @return true or false if the last token resulted in a win
     * @post [decide if the token placed makes a win by calling the 3 check functions]
     */
    // Checks if the last move had resulted in a win
    default public boolean checkForWin(int c) {
        // Column has to not be -1, because this is the initial value given in main()
        if (c != -1) {
            int row = 0;
            for (int i = getNumRows() - 1; i >= 0; i--) {
                BoardPosition pos = new BoardPosition(i, c);
                if (whatsAtPos(pos) != ' ') {
                    row = i;
                    break;
                }
            }
            // Makes a new position so we can pass it into the 3 win check functions
            BoardPosition pos = new BoardPosition(row, c);
            char player = whatsAtPos(pos);

            // Calls each check win function to confirm whether or not the player has won
            return checkHorizWin(pos, player) || checkVertWin(pos, player) || checkDiagWin(pos, player);
        }
        return false;
    }

    /**
     * @pre [checkIfFree() = false for all columns]
     * @return true or false depending on if the whole board is full
     * @post [checks for a full board, returns t/f]
     */
    // Checks to see if there is an outcome of a tied game
    default public boolean checkTie() {
        for (int i = 0; i < getNumColumns(); i++) {
            if (checkForWin(i) || checkIfFree(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @pre p = [valid character] AND 1 <= c <= numColumns
     * @param p character used as the token
     * @param c number used for the column to place token in
     * @post [put token in column c]
     */
    // Places the token in the player's chosen column (if open)
    public void placeToken(char p, int c);

    /**
     * @pre [pos is valid] AND p = [valid character]
     * @param pos a BoardPosition that represents the position where the token was placed
     * @param p character representing the token of the player
     * @return true or false depending on the placement of tokens horizontally
     * @post [checks for side-to-side correct amount matching tokens in a row]
     */
    // Checks for player receiving a horizontal win
    default public boolean checkHorizWin(BoardPosition pos, char p) {
        boolean value = false;
        int counter = 0;
        int row = pos.getRow();

        // Checks each column for a match
        for (int i = 0; i < getNumColumns(); i++) {
            BoardPosition newPos = new BoardPosition(row, i);
            if (whatsAtPos(newPos) == p) {
                counter++;
            }
            else {
                counter = 0;
            }
            if (counter == getNumToWin()) {
                value = true;
            }
        }
        return value;
    }

    /**
     * @pre [pos is valid] AND p = [valid character chosen by user]
     * @param pos a BoardPosition that represents the position where the token was placed
     * @param p character representing the token of the player
     * @return true or false depending on the placement of tokens vertically
     * @post [checks for up/down for the amount of matching tokens in a row]
     */
    // Checks for player receiving a vertical win
    default public boolean checkVertWin(BoardPosition pos, char p) {
        boolean value = false;
        int counter = 0;
        int column = pos.getColumn();

        // Checks each row for a match
        for (int i = 0; i < getNumRows(); i++) {
            BoardPosition newPos = new BoardPosition(i, column);
            if (whatsAtPos(newPos) == p) {
                counter++;
            }
            else {
                counter = 0;
            }
            if (counter == getNumToWin()) {
                value = true;
            }
        }
        return value;
    }

    /**
     * @pre [pos is valid] AND p = [valid character chosen by user]
     * @param pos a BoardPosition that represents the position where the token was placed
     * @param p character representing the token of the player
     * @return true or false depending on the placement of tokens diagonally
     * @post [checks for diagonal (user specified amount) matching tokens in a row]
     */
    // Checks for player receiving a diagonal win
    default public boolean checkDiagWin(BoardPosition pos, char p) {
        // Counter to compare to the numToWin variable
        int counter = 0;
        // Downwards diagonal checking
        // Nested loops for counting from top left to bottom right
        for (int i = (getNumToWin() - 1); i < getNumRows(); i++){
            for (int j = 0; j < getNumColumns() - (getNumToWin() - 1); j++){
                // Resets counter
                counter = 0;

                for (int k = 0; k < getNumToWin(); k++) {
                    BoardPosition newPosR = new BoardPosition(i-k, j+k);
                    if (whatsAtPos(newPosR) == p) {
                        counter++;
                    }
                    else {
                        counter = 0;
                    }
                    if (counter == getNumToWin()) {
                        return true;
                    }
                }
            }
        }

        // Upwards diagonal checking
        // Nested loops for counting from top right to bottom left
        for (int i = (getNumToWin() - 1); i < getNumRows(); i++){
            for (int j = (getNumToWin() - 1); j < getNumColumns(); j++){
                // Resets counter
                counter = 0;

                for (int k = 0; k < getNumToWin(); k++) {
                    BoardPosition newPosR = new BoardPosition(i-k, j-k);
                    if (whatsAtPos(newPosR) == p) {
                        counter++;
                    }
                    else {
                        counter = 0;
                    }
                    if (counter == getNumToWin()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * @pre [pos is valid]
     * @param pos a BoardPosition variable that represents the position
     * @return character of the position
     * @post [finds which player's token or if it's blank at the specified position]
     */
    // Finds what character is at the position given
    public char whatsAtPos(BoardPosition pos);

    /**
     * @pre [pos is valid] AND player = [valid character chosen by user]
     * @param pos BoardPosition variable representing the given position to scan
     * @param player character to search for
     * @return true or false if the player is detected in given position
     * @post [detects if the player is at the position]
     */
    // Finds if a player is in a certain position
    default public boolean isPlayerAtPos(BoardPosition pos, char player) {
        if (whatsAtPos(pos) == player) {
            return true;
        }
        else {
            return false;
        }
    }
}

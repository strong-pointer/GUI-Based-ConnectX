package core.extendedConnectX;

public abstract class AbsGameBoard implements IGameBoard {
    /**
     * @pre [a valid GameBoard variable needs to be initialized]
     * @return a formatted String called output that represents the Game Board
     * @post [creates the visualization of a GameBoard variable]
     */
    @Override
    public String toString() {
        String output = new String();

        // Sets up the top row with the labels
        if (this.getNumColumns() <= 9) {
            for (int i = 0; i < this.getNumColumns(); i++) {
                output += "| " + i;
            }
            output += "|";
        }
        // After 9 columns, the spacing is treated differently
        else {
            for (int i = 0; i < 10; i++) {
                output += "| " + i;
            }

            for (int i = 10; i < this.getNumColumns(); i++) {
                output += "|" + i;
            }
            output += "|";
        }

        // Adds the actual players' places and blank spaces into the string
        for (int i = this.getNumRows() - 1; i > -1; i--) {
            output += "\n";
            for (int j = 0; j < this.getNumColumns(); j++) {
                BoardPosition pos = new BoardPosition(i, j);
                output += "|" + this.whatsAtPos(pos) + " ";
            }
            output += "|";
        }

        return output + "\n";
    }
}

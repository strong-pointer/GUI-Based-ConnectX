package core.extendedConnectX;

public class BoardPosition {
    /**
     * @invariant 1 <= Row <= 100
     * @invariant 1 <= Column <= 100
     */
    // Private variables
    private int row;
    private int column;

    /**
     * @pre 1 <= Row <= [amount of rows specified by user] AND 1 <= Column <= [amount of columns specified by user]
     * @param Row row of the BoardPosition
     * @param Column column of the BoardPosition
     * @post Row = #Row AND Column = #Column
     */
    // Constructor for BoardPosition variables
    public BoardPosition(int Row, int Column) {
        row = Row;
        column = Column;
    }

    /**
     * @pre [Row needs to be initialized]
     * @return the Row private variable
     * @post [private variable Row is given]
     */
    // Getter for private variable Row
    public int getRow(){
        return row;
    }

    /**
     * @pre [Column needs to be initialized]
     * @return the Column private variable
     * @post [private variable Column is given]
     */
    // Getter for private variable Column
    public int getColumn(){
        return column;
    }

    /**
     * @pre [valid BoardPosition]
     * @param obj the position being compared
     * @return true if both BoardPositions compared are equal
     * @post [tests if the row AND column are equal]
     */
    // Overridden equals() for comparing two BoardPosition variables
    @Override
    public boolean equals(Object obj) {
        if (obj == null || (obj.getClass() != this.getClass())) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        BoardPosition copy = (BoardPosition) obj;
        return (this.row == copy.row) && (this.column == copy.column);
    }

    /**
     * @pre [valid BoardPosition variable initialized]
     * @return the formatted string with the row and column, separated by a comma
     * @post [creates a row and column combo with a comma separating them]
     */
    @Override
    public String toString() {
       String output = new String();
       output += getRow() + "," + getColumn();

       return output;
    }
}

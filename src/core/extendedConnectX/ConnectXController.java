package core.extendedConnectX;

/**
 * The controller class will handle communication between our View and our Model (IGameBoard)
 * <p>
 * This is where you will write code
 * <p>
 * You will need to include your IGameBoard interface
 * and both of the IGameBoard implementations from Project 4
 * If your code was correct you will not need to make any changes to your IGameBoard implementation class
 */
public class ConnectXController {

    //our current game that is being played
    private IGameBoard curGame;

    //The screen that provides our view
    private ConnectXView screen;

    //our play tokens are hard coded. We could make a screen to get those from the user, but
    private char[] players = new char[] {'X', 'O', 'A', 'M', 'J', 'L', 'P', 'S', 'K', 'Z'};
    private int turnNumber = 0;
    private boolean endGame = false;

    private int numPlayers;

    /**
     * @param model the board implementation
     * @param view  the screen that is shown
     * @post the controller will respond to actions on the view using the model.
     */
    ConnectXController(IGameBoard model, ConnectXView view, int np) {
        this.curGame = model;
        this.screen = view;
        numPlayers = np;
    }

    /**
     * @param col the column of the activated button
     * @post will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button
     */
    public void processButtonClick(int col) {
        // If the game was over, the next button click should reset the game to play again
        if (endGame) {
            newGame();
        }
        endGame = false;
        // Row variable used for the row on the GUI
        int row;

        // Checks if the column chose is available to be placed in/changes players at the end
        if (curGame.checkIfFree(col)) {
            // Places the token on the Model for the board
            curGame.placeToken(players[turnNumber], col);
            // Creates a new board position to compare it later
            BoardPosition pos = new BoardPosition(0, col);

            // pos is compared to find which row is the next to be placed in
            for (int i = 0; i < curGame.getNumRows(); i++) {
                pos = new BoardPosition(i, col);
                if (curGame.whatsAtPos(pos) == ' ') {
                    break;
                }
                else {
                    pos = new BoardPosition(curGame.getNumRows(), col);
                }
            }
            row = pos.getRow() - 1;
            // Sets the marker into the View
            screen.setMarker(row, col, players[turnNumber]);

            // Checks the board for a tie or win (ending conditon)
            if (curGame.checkTie() || curGame.checkForWin(col)) {
                // Tie game message
                if (curGame.checkTie()) {
                    screen.setMessage("The game has ended in a tie.");
                }
                // Winning message
                else {
                    screen.setMessage(players[turnNumber] + " has won!");
                }
                endGame = true;
                turnNumber = 0;
            }
            // No wins or tie yet
            else {
                // Changes the player each time a token has been placed
                if (turnNumber == (numPlayers - 1)) {
                    turnNumber = 0;
                } else {
                    turnNumber++;
                }
                screen.setMessage("It is " + players[turnNumber] + "'s turn.");
            }
        }
        // Goes here if the column is unavailable/doesn't change players yet
        else {
            screen.setMessage("ERROR: Column " + col + " is full!");
        }
    }

    /**
     * This method will start a new game by returning to the setup screen and controller
     */
    private void newGame() {
        //close the current screen
        screen.dispose();
        //start back at the set up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}
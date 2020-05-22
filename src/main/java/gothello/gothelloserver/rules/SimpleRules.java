package gothello.gothelloserver.rules;

/**
 * SimpleRules is an implementation of rules that does nothing but simply store
 * the state of the game in a 2D array
 */
public class SimpleRules implements Rules {
  Stone[][] board = new Stone[8][8];
  private Stone currentTurn = Stone.BLACK;

  public SimpleRules() {
    // Set board initial state
    for (int x = 0; x < board.length; x++) {
      for (int y = 0; y < board[x].length; y++) {
        board[x][y] = Stone.NONE;
      }
    }

    board[1][1] = Stone.WHITE;
    board[2][2] = Stone.WHITE;
    board[1][2] = Stone.BLACK;
    board[2][1] = Stone.BLACK;

    board[5][6] = Stone.WHITE;
    board[6][5] = Stone.WHITE;
    board[5][5] = Stone.BLACK;
    board[6][6] = Stone.BLACK;
  }

  //
  // Get Game State
  //

  // getSquare returns the stone at (x,y) on the board
  public Stone getSquare(int x, int y) {
    return board[x][y];
  }

  // getTurn returns the player who's turn it is
  public Stone getTurn() {
    return currentTurn;
  };

  // getWinner returns the player who has won or Stone.NONE
  public Stone getWinner() {
    return Stone.NONE;
  }

  // getScore returns the score of the specified player
  public int getScore(Stone player) {
    return 0;
  }

  // getBoardSize returns the size of a square board
  public int getBoardSize() {
    return 8;
  };

  // isLegal returns true or false depending on if the square is a legal move
  // for the specified player
  public boolean isLegal(int x, int y, Stone player) {
    return (x >= 0 && y >= 0 && x < 8 && y < 8 && board[x][y] == Stone.NONE && player == currentTurn);
  }

  // isGameOver returns true if the game is finished
  public boolean isGameOver() {
    return false;
  }

  //
  // Change Game State
  //

  // pass skips a player's turn
  public void pass(Stone player) {

  }

  // resign forfeits the game
  public void resign(Stone player) {

  }

  // playStone places a stone at (x,y)
  public boolean playStone(int x, int y, Stone player) {
    if (!isLegal(x, y, player))
      return false;
    board[x][y] = player;
    currentTurn = (currentTurn == Stone.BLACK ? Stone.WHITE : Stone.BLACK);
    return true;
  }

}
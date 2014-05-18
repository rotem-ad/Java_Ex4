package model.algorithm;

public class State {
	
	private int[][] board;
	private int score;
	private int player;
	//private int playerRow;
	//private int playerCol;
	public static int UNDEFINED_MOVE = -1;
	public static int MAX = 1;
	public static int MIN = 2;
	public int prevMove;
	
	public int getPlayer()
	{
		return this.player;
	}
	
	public int utility()
	{
		return evaluatePosition();
		
	}
	 int[][] Three_in_a_Row =  {
		    { 0, 1, 2 },
		    { 3, 4, 5 },
		    { 6, 7, 8 },
		    { 0, 3, 6 },
		    { 1, 4, 7 },
		    { 2, 5, 8 },
		    { 0, 4, 8 },
		    { 2, 4, 6 }
		};
	 int[][] Heuristic_Array = {
		    {     0,   -10,  -100, -1000 },
		    {    10,     0,     0,     0 },
		    {   100,     0,     0,     0 },
		    {  1000,     0,     0,     0 }
		};

	public	int evaluatePosition()    {
		    int opponent = (player == MAX) ? MIN : MAX;
		    int piece;
		    int players, others, t = 0, i, j;
		    for(i=0; i<8; i++)  {
		        players = others = 0;
		        for(j=0; j<3; j++)  {
		            piece = board[i][j];
		            if (piece == player)
		                players++;
		            else if (piece == opponent)
		                others++;
		        }
		        t += Heuristic_Array[players][others];
		    }
		    return t;
		}
	public void getPlayer(int player)
	{
		this.player = player;
	}
	
	/*public void setPlayerPosition(int row, int col){
		this.playerCol = col;
		this.playerRow=row;
	}
	public int getPlayerCol()
	{
		return playerCol;
	}
	public int getPlayerRow()
	{
		return playerRow;
	}*/
	public int[][] getBoard() {
		return board;
	}
	public void setBoard(int[][] board) {
		this.board = board;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	//constractor -- create new state
	public State() {
	}
	public State(int boardSize) {
		this.board = new int[boardSize][boardSize];
		this.score = 0; //at the beginning of the game score is 0
	}
	
	public int getBoardSize()
	{
		return this.board.length;
	}
	
	public State Clone()
	{
		State newS = new State();
		int[][] board = new int[this.board.length][this.board.length];
		for(int i=0; i<this.board.length; i++)
			for(int j=0; j<this.board.length; j++)
				board[i][j] = this.board[i][j];
		newS.setBoard(board);
		newS.setScore(this.score);
		//newS.setPlayerPosition(this.playerRow, this.playerCol);
		return newS;
	}
	
	@Override
	public boolean equals(Object state)
	{
		
		State s =(State)state;
		for(int i=0; i<board.length; i++)
			for(int j=0; j<board.length; j++)
				if (s.board[i][j] != board[i][j])
					return false;
		return this.score == s.score ;//&& this.playerCol == s.playerCol && this.playerRow==s.playerRow;
	}
}

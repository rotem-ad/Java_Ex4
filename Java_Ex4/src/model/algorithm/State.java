package model.algorithm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class State implements Serializable {
	
	private static final long serialVersionUID = 1L; // for Serializable
	private int[][] board;
	private int score;
	private int player;
	public static int UNDEFINED_MOVE = -1;
	public static int MAX = 1;
	public static int MIN = 2;
	
	public int prevMove;
	
	public int getPlayer()
	{
		return this.player;
	}
	
	
	 public int getNumberOfEmptyCells()
	 {
		 int count =0;
		 for(int i=0; i< getBoardSize(); i++)
			 for(int j=0; j< getBoardSize(); j++)
				 if (board[i][j] == -1)
					 count++;
		 return count;
	 }
	public List<Integer> getEmptyCellIds()
	 {
		 List<Integer> cellIds = new ArrayList<Integer>();
		 
		 for(int i=0; i< getBoardSize(); i++)
			 for(int j=0; j< getBoardSize(); j++)
				 if (board[i][j] == 0)
					 cellIds.add(i*getBoardSize()+j);
		 return cellIds;
	 }
	
	public void getPlayer(int player)
	{
		this.player = player;
	}
	

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

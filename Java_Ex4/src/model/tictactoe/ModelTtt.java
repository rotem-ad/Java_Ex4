package model.tictactoe;

import java.util.ArrayList;

import model.algorithm.Action;
import model.algorithm.Model;
import model.algorithm.State;


public class ModelTtt implements Model{
	
	State cuurState;
	
	public static int X =1;
	public static int O =0;
	public static int EMPTY =-1;
	
	
	
	
	//constractor -- create new state
	public ModelTtt() {
		
	}
	
	public ModelTtt(int boardSize) {
		this.cuurState = new State(boardSize);
	}
	
	public int getBoardSize()
	{
		return this.cuurState.getBoardSize();
	}

	@Override
	public boolean isGameOver(State state) {
		return (isPlayerWon(state.getPlayer()) || noMoreMoves());
	}

	@Override
	public ArrayList<Action> getPossibleActions(State state, int player) {
		ArrayList<Action> actions = new ArrayList<Action>();
		int i,j;
		for( i=0; i<getBoardSize(); i++)
		    for( j=0; j<getBoardSize(); j++)
		    	if (state.getBoard()[i][j] == EMPTY)
		    	{
		    		ActionTtt ac = new ActionTtt(i, j, player);
		    		actions.add(ac);
		    	} 
		return actions;
	}
	
	private boolean isPlayerWon(int player)
	{
		int counter=0;
		for(int i=0; i<getBoardSize(); i++)
		{
			for(int j=0; j<getBoardSize(); j++)
				if(cuurState.getBoard()[i][j] == player)
					counter++;
			if (counter==getBoardSize())
				return true;
			counter=0;
		}
		for(int j=0; j<getBoardSize(); j++)
		{
			for(int i=0; i<getBoardSize(); i++)
				if(cuurState.getBoard()[i][j] == player)
					counter++;
			if (counter==getBoardSize())
				return true;
			counter=0;
		}
		for(int i=0; i<getBoardSize(); i++)
		{
			for(int j=getBoardSize()-1; j>0; j--)
				if(i==j && cuurState.getBoard()[i][j] == player)
					counter++;
		}
		if (counter==getBoardSize())
			return true;
		counter=0;
		return false;
	}
	
	private boolean noMoreMoves()
	{
		for(int i=0; i<getBoardSize(); i++)
			for(int j=0; j<getBoardSize(); j++)
				if (cuurState.getBoard()[i][j] == EMPTY)
					return false;
		return true;
		
	}

	@Override
	public boolean isGameWon(State state) {
		return isPlayerWon(state.getPlayer());
		
	}
}

package model.game2048;

import java.util.ArrayList;

import model.algorithm.AbsModel;
import model.algorithm.Action;
import model.algorithm.State;

public class Model2048 extends AbsModel{

	public int WINNIG_NUMBER = 2048;
	public int EMPTY_CELL = 0;
	
	public Model2048(int boardSize){
		super(boardSize);
	}
	
	@Override
	public boolean isGameWon(State state) {
		return gameWon(state);
		
	}

	@Override
	public boolean isGameOver(State state) {
		return gameWon(state) || boardIsFull(state);
		
	}

	@Override
	public ArrayList<Action> getPossibleActions(State state, int player) {
		ArrayList<Action> possibleActions = new ArrayList<Action>();
		possibleActions.add(new UpAction2048());
		possibleActions.add(new DownAction2048());
		possibleActions.add(new LeftAction2048());
		possibleActions.add(new RightAction2048());
		return possibleActions;
	}
	
	private boolean gameWon(State state){
		for(int i=0; i< state.getBoardSize(); i++)
			for(int j=0; j< state.getBoardSize(); j++)
			{
				if (state.getBoard()[i][j] == WINNIG_NUMBER)
					return true;
			}
		return false;
	}
	
	private boolean boardIsFull(State state){
		boolean emptyCellExists=false;
		boolean similarNumbersExists=false;
		for(int i=0; i< state.getBoardSize(); i++)
			for(int j=0; j< state.getBoardSize() && !emptyCellExists; j++)
			{
				if (state.getBoard()[i][j] == EMPTY_CELL)
				{
						emptyCellExists= true;
						
				}
			}
		if (!emptyCellExists){
			for(int i=0; i< state.getBoardSize(); i++)
				for(int j=0; j< state.getBoardSize()-1; j++)
				{
					similarNumbersExists = (state.getBoard()[i][j] == state.getBoard()[i][j+1]);
							
					if (similarNumbersExists)
						return false;
				}
			for(int i=0; i<state.getBoardSize()-1; i++)
				for (int j=0; j<state.getBoardSize(); j++)
				{
					similarNumbersExists = (state.getBoard()[i][j] == state.getBoard()[i+1][j]);
					if (similarNumbersExists)
						return false;
				}
			return true;
		}
		return false;
	}

	
}

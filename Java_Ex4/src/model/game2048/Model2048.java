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
		Action up = new UpAction2048();
		Action down = new DownAction2048();
		Action left = new LeftAction2048();
		Action right = new RightAction2048();
		
		if(isActionValid(state, up))
			possibleActions.add(up);
		if(isActionValid(state, down))
			possibleActions.add(down);
		if(isActionValid(state, left))
			possibleActions.add(left);
		if(isActionValid(state, right))
			possibleActions.add(right);
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

	private boolean isActionValid(State state, Action action)
	{
		State newState =	action.doAction(state);
		if (newState.equals(state))
			return false;
		else
			return true;
	}
	
    /**
     * Calculates a heuristic variance-like score that measures how clustered the
     * board is.
     * 
     * @param boardArray
     * @return 
     */
	@Override
    public  int calculateClusteringScore(int[][] boardArray) {
        int clusteringScore=0;
        
        int[] neighbors = {-1,0,1};
        
        for(int i=0;i<boardArray.length;++i) {
            for(int j=0;j<boardArray.length;++j) {
                if(boardArray[i][j]==0) {
                    continue; //ignore empty cells
                }
                
                //clusteringScore-=boardArray[i][j];
                
                //for every pixel find the distance from each neightbors
                int numOfNeighbors=0;
                int sum=0;
                for(int k : neighbors) {
                    int x=i+k;
                    if(x<0 || x>=boardArray.length) {
                        continue;
                    }
                    for(int l : neighbors) {
                        int y = j+l;
                        if(y<0 || y>=boardArray.length) {
                            continue;
                        }
                        
                        if(boardArray[x][y]>0) {
                            ++numOfNeighbors;
                            sum+=Math.abs(boardArray[i][j]-boardArray[x][y]);
                        }
                        
                    }
                }
                
                clusteringScore+=sum/numOfNeighbors;
            }
        }
        
        return clusteringScore;
    }

	  
    /**
     * Estimates a heuristic score by taking into account the real score, the
     * number of empty cells and the clustering score of the board.
     * 
     * @param actualScore
     * @param numberOfEmptyCells
     * @param clusteringScore
     * @return 
     */
	@Override
    public  int heuristicScore(int actualScore, int numberOfEmptyCells, int clusteringScore) {
        int score = (int) (actualScore+Math.log(actualScore)*numberOfEmptyCells -clusteringScore);
        return Math.max(score, Math.min(actualScore, 1));
    }
}

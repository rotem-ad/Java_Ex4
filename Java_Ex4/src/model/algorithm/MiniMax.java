package model.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.GameAction;

public class MiniMax extends AbsSolver{

	//private members
	private enum Player{
		USER, COMPUTER
	}
	private Model model;
	private int depth;
	
	//contractor
	public MiniMax(Model model, int depth) {
		super();
		this.model = model;
		this.depth = depth;
	}
	
	@Override
	public GameAction Solve(State start) {
		// TODO Auto-generated method stub
		
		try{
		   
			Map<String, Object> result =  minimax(start, this.depth, Player.USER);
			Action act1 = (Action)result.get("Action");
			return act1.getId();
		}
		catch(Exception ex)
		{
			return null;
		}
		
	}

	/**
     * Finds the best move by using the Minimax algorithm.
     * 
     * @param theBoard
     * @param depth
     * @param player
     * @return
     * @throws CloneNotSupportedException 
     */
    private  Map<String, Object> minimax(State state, int depth, Player player) throws CloneNotSupportedException {
    	
    	int[][] theBoard = state.getBoard();
    	
    	Map<String, Object> result = new HashMap<>();
        
        Action bestAction = null;
        
        raiseNumOfEvaluetedNodes();
        
        int bestScore;
        
        if(depth==0 ||  model.isGameOver(state)){
            bestScore=heuristicScore(state.getScore(), state.getNumberOfEmptyCells(), calculateClusteringScore(theBoard));
        }
        else {
            if(player == Player.USER) {
                bestScore = Integer.MIN_VALUE;
                ArrayList<Action> possibleActions = model.getPossibleActions(state, (player==Player.USER)? 1: 0);
                for(Action action : possibleActions) {
                    State newState = action.doAction(state);
                    
                  
                    int points =  newState.getScore();
                    
                    if(points==0 && newState.equals(state)) {
                    	continue;
                    }

                    Map<String, Object> currentResult = minimax(newState, depth-1, Player.COMPUTER);
                    int currentScore=((Number)currentResult.get("Score")).intValue();
                    if(currentScore>bestScore) { //maximize score
                        bestScore=currentScore;
                        bestAction=action;
                    }
                }
            }
            else {
                bestScore = Integer.MAX_VALUE;

                List<Integer> moves = state.getEmptyCellIds();
                
                if(moves.isEmpty()) {
                    bestScore=0;
                }
                
                int[] possibleValues = {2, 4};

                int i,j;
                
              
                
                for(Integer cellId : moves) {
                    i = cellId/state.getBoardSize();
                    j = cellId%state.getBoardSize();

                    for(int value : possibleValues) {
                        State newState = state.Clone();
                    	int[][] newBoard = newState.getBoard();
                        
                    	newBoard[i][j] = value;

                        Map<String, Object> currentResult = minimax(newState, depth-1, Player.USER);
                        int currentScore=((Number)currentResult.get("Score")).intValue();
                        if(currentScore<bestScore) { //minimize best score
                            bestScore=currentScore;
                        }
                    }
                }
            }
        }
        
        result.put("Score", bestScore);
        result.put("Action", bestAction);
        
        return result;
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
    private  int heuristicScore(int actualScore, int numberOfEmptyCells, int clusteringScore) {
        int score = (int) (actualScore+Math.log(actualScore)*numberOfEmptyCells -clusteringScore);
        return Math.max(score, Math.min(actualScore, 1));
    }
    
    /**
     * Calculates a heuristic variance-like score that measures how clustered the
     * board is.
     * 
     * @param boardArray
     * @return 
     */
    private  int calculateClusteringScore(int[][] boardArray) {
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

}

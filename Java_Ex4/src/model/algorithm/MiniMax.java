package model.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.GameAction;

public class MiniMax extends AbsSolver{

	
	
	
	//contractor
	public MiniMax(Model model, int depth) {
		super();
		super.model = model;
		super.depth = depth;
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
            bestScore=model.heuristicScore(state.getScore(), state.getNumberOfEmptyCells(), model.calculateClusteringScore(theBoard));
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
    
    
    
  
    

}

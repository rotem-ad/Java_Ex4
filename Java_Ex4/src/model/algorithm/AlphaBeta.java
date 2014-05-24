package model.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Entities.GameAction;

public class AlphaBeta extends AbsSolver{

	
	public AlphaBeta(Model model,   int depth) {
		super();
		super.model = model;
		super.depth=depth;
	}
	
	@Override
	public GameAction Solve(State start) {
		try {
			Map<String, Object> result;
			result = alphabeta(start, super.depth, Integer.MIN_VALUE, Integer.MAX_VALUE, Player.USER);
			Action act1 = (Action)result.get("Action");
			return act1.getId();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	 /**
     * Finds the best move bay using the Alpha-Beta pruning algorithm.
     * 
     * @param theBoard
     * @param depth
     * @param alpha
     * @param beta
     * @param player
     * @return
     * @throws CloneNotSupportedException 
     */
    private  Map<String, Object> alphabeta(State state, int depth, int alpha, int beta, Player player) throws CloneNotSupportedException {
        Map<String, Object> result = new HashMap<>();
        
        Action bestAction = null;
        int bestScore;
        
        if(model.isGameOver(state)) {
            if(model.isGameWon(state)) {
                bestScore=Integer.MAX_VALUE; //highest possible score
            }
            else {
                bestScore=Math.min(state.getScore(), 1); //lowest possible score
            }
        }
        else if(depth==0) {
            bestScore=model.heuristicScore(state.getScore(),state.getNumberOfEmptyCells(),model.calculateClusteringScore(state.getBoard()));
        }
        else {
            if(player == Player.USER) {
            	ArrayList<Action> possibleActions = model.getPossibleActions(state,(player==Player.USER)? 1: 0);
                for(Action action : possibleActions) {
                	 State newState = action.doAction(state);

                    int points=newState.getScore();
                    
                    if(points==0 && newState.equals(state)) {
                    	continue;
                    }
                    
                    Map<String, Object> currentResult = alphabeta(newState, depth-1, alpha, beta, Player.COMPUTER);
                    int currentScore=((Number)currentResult.get("Score")).intValue();
                                        
                    if(currentScore>alpha) { //maximize score
                        alpha=currentScore;
                        bestAction=action;
                    }
                    
                    if(beta<=alpha) {
                        break; //beta cutoff
                    }
                }
                
                bestScore = alpha;
            }
            else {
                List<Integer> moves = state.getEmptyCellIds();
                int[] possibleValues = {2, 4};

                int i,j;
                abloop: for(Integer cellId : moves) {
                    i = cellId/state.getBoardSize();
                    j = cellId%state.getBoardSize();

                    for(int value : possibleValues) {
                    	 State newState = state.Clone();
                    	 int[][] newBoard = newState.getBoard();
                    	 newBoard[i][j] = value;

                        Map<String, Object> currentResult = alphabeta(newState, depth-1, alpha, beta, Player.USER);
                        int currentScore=((Number)currentResult.get("Score")).intValue();
                        if(currentScore<beta) { //minimize best score
                            beta=currentScore;
                        }
                        
                        if(beta<=alpha) {
                            break abloop; //alpha cutoff
                        }
                    }
                }
                
                bestScore = beta;
                
                if(moves.isEmpty()) {
                    bestScore=0;
                }
            }
        }
        
        result.put("Score", bestScore);
        result.put("Action", bestAction);
        
        return result;
    }
}

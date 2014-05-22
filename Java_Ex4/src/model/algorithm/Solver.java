package model.algorithm;

import Entities.GameAction;


public interface Solver {

	//get next action based on given state
	public GameAction Solve(State start);
	
	public int getNumOfEvaluetedNodes();
	
}

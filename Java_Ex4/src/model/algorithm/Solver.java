package model.algorithm;


public interface Solver {

	//get next action based on given state
	public Action Solve(State start);
	
	public int getNumOfEvaluetedNodes();
	
}

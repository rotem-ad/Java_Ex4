package model.algorithm;

import java.util.ArrayList;

public interface Solver {

	//get all actions to get from start state to goal state 
	public Action Solve(State start);
	
	public int getNumOfEvaluetedNodes();
	
	
	//public void setHuristics(IDistance[] dist);
	
	//public void setDomain(IDomain d);
}

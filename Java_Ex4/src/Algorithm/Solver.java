package Algorithm;

import java.util.ArrayList;

public interface Solver {

	//get all actions to get from start state to goal state 
	public ArrayList<Action> Solve(State start, State goal);
	
	public int getNumOfEvaluetedNodes();
	
	
	//public void setHuristics(IDistance[] dist);
	
	//public void setDomain(IDomain d);
}

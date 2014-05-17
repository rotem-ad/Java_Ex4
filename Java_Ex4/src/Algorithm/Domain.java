/*Maria Kuskina 313926677 */

package Algorithm;

import java.util.ArrayList;

public interface Domain {
	
	//get all legal actions for given state 'state'
	public ArrayList<Action> getActions(State state); 
	
	//return action that leads from state 1 to state 2
	public Action getAction(State state1, State state2); 
	
	//get distance between two states
	public double getDistance(State from, State to);
	
	
	public State getStartState();
	public State getGoalState();
	
}

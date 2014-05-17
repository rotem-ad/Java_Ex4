

package model.algorithm;
import java.io.Serializable;


import java.util.ArrayList;
import java.util.Stack;


public class Astar extends AbsSolver implements Serializable{
	
	private Distance h;
	private Distance g;
	private Domain domain;
	
	public Distance getH() {
		return this.h;
	}

	public void setH(Distance h) {
		this.h = h;
	}

	public Distance getG() {
		return g;
	}

	public void setG(Distance g) {
		this.g = g;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain d)
	{
		this.domain = d;
	}
	 
	public Astar()
	{
		this.domain = null;
		this.h = null;
		this.g = null;
	}
	
	public Astar(Domain domain, Distance domainH, Distance domainG)
	{
		this.domain = domain;
		this.h = domainH;
		this.g = domainG;
	}
	
	public void setHuristics(Distance[] dist)
	{
		this.h = dist[0];
		this.g = dist[1];
	}
	
	
	
	@Override
	//get all actions to get from start state to goal state 
	public ArrayList<Action> Solve(State start, State goal){
		
		//HashMap<Integer, State> closed = new  HashMap<>();  //set of nodes already evaluated
		//PriorityQueue<State> open =   new PriorityQueue<State>(); //set of tentative nodes to be evaluated
		
		start.setG(0); //init start g score
		start.setF(g.getDistance(start, start) + h.getDistance(start, goal));
		start.setCameFrom(null);
		this.addToOpen(start);
		
		
		while (this.getOpenSize() != 0)  //while open queue is not empty
        {
			State q = this.getFromOpen();  //pop state with lowest value
			
			if (q.equals(goal))  //if we reached the goal
			{
				Stack<Action> actionsStack = reconstractPath(q);
				
				return  StackToArrayList(actionsStack);
			}
			this.addToClosed(q.hashCode(), q); //add q to closed 
			ArrayList<Action> legalActions = domain.getActions(q); //get all legal actions
			for(Action action: legalActions) //loop all legal actions
			{
				State qTag = action.doAction(q); //apply action on state q and get result state
				double tentativeGScore = g.getDistance(q,q) + domain.getDistance(q,  qTag);
				
				if (this.isInClosed(qTag.hashCode()) && tentativeGScore>=g.getDistance(qTag, qTag)) //q' in closed list && tentative >= q' gscore
					continue;
				if (!this.isInOpened(qTag) || tentativeGScore<g.getDistance(qTag, qTag)) //q' not in open or tentative < q' gscore
				{
					qTag.setCameFrom(q);
					qTag.setG(tentativeGScore);
					qTag.setF(g.getDistance(qTag, qTag) + h.getDistance(qTag, goal));
					if (!this.isInOpened(qTag))
						this.addToOpen(qTag);
				}
				
			}
				
        }

		return null; //not implemented
	}
	
	//helper function: recreate path
	private Stack<Action> reconstractPath(State current) {
		//ArrayList<Action> actionList = new ArrayList<Action>(); 
		Stack<Action> actionStack = new Stack<Action>();
		
		if (current.getCameFrom() != null) {
			actionStack = reconstractPath(current.getCameFrom());
		}
		
		Action  currentAction = domain.getAction(current.getCameFrom(), current);
		actionStack.push(currentAction);
		return actionStack;
	}
	
	private ArrayList<Action> StackToArrayList(Stack<Action> actionsStack){
		 ArrayList<Action> result = new ArrayList<Action>();
		 for (Action action: actionsStack){
			 result.add(action);
		 }
		return result;
	}

}

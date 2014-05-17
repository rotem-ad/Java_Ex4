package model.algorithm;

import java.util.HashMap;
import java.util.PriorityQueue;

public abstract class AbsSolver implements Solver{
	
	
	private HashMap<Integer, State> closed;  //set of nodes already evaluated
	private PriorityQueue<State> open;  //set of tentative nodes to be evaluated
	private int numOfEvaluatedNodes;
	
	
	public AbsSolver() {
		super();
		this.closed = new  HashMap<>();
		this.open = new PriorityQueue<State>();;
		this.numOfEvaluatedNodes = 0;
	}
	
	public void addToClosed(Integer key, State state)
	{
		this.closed.put(key, state);
	}
	public void addToOpen(State state)
	{
		this.open.add(state);
	}
	
	//pop state with lowest value
	public State getFromOpen()
	{
		State state = open.remove(); 
		this.numOfEvaluatedNodes ++;
		return state;
	}
	public State getFromClosed(int key)
	{
		State state = this.closed.get(key);
		return state;
	}
	public int getNumOfEvaluetedNodes() {
		return this.numOfEvaluatedNodes;
	}
	
	public int getOpenSize()
	{
		return this.open.size();
	}
	public boolean isInClosed(int key)
	{
		return this.closed.containsKey(key);
	}
	public boolean isInOpened(State state)
	{
		return this.open.contains(state);
	}
	public void raiseNumOfEvaluetedNodes()
	{
		this.numOfEvaluatedNodes++;
	}
}

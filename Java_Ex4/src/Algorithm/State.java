package Algorithm;

import java.util.ArrayList;
import java.util.List;

public class State implements Comparable<State> {
	
	//private members
	
	public State(String state) {
		super();
		this.state = state;
	}

	private String state;
	private double g;
	private double h;
	private double f;
	
	State cameFrom; 
	
	
	
	//getters & setters
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public State getCameFrom() {
		return cameFrom;
	}
	public void setCameFrom(State cf) {
		this.cameFrom = cf;
	}
	public double getG() {
		return g;
	}
	public void setG(double g) {
		this.g = g;
	}
	public double getH() {
		return h;
	}
	public void setH(double h) {
		this.h = h;
	}
	public double getF() {
		return f;
	}
	public void setF(double f) {
		this.f = f;
	}
	
	
	@Override
	public int compareTo(State s) {
		return Double.compare(this.f, s.getF());
	}
	
	public boolean equals(Object obj) {
		return (this.hashCode() == obj.hashCode());
	}
	
	public int hashCode() {
		return this.state.hashCode();//(""+this.g + this.h+ this.f+this.state).hashCode();
    }

	public List<Action> getLegalActions(){
		return null;
	}
	
	public static int UNDEFINED_MOVE = -1;
	public static int MAX = 1;
	public static int MIN = 2;
	private int player;
	private int currUtility;
	public int prevMove=0;
	
	
	public void setPlayer(int playerType)
	{
		this.player=playerType;
	}
	
	public int getPlayer(){
		return this.player;
	}
	
	public boolean gameOver(){
		return false;
	}
	
	public int utility()
	{
		return this.currUtility;
	}

	
}

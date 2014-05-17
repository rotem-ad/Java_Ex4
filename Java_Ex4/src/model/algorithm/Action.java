package model.algorithm;


public interface Action {
	
	String getName();
	
	State doAction(State state);
}

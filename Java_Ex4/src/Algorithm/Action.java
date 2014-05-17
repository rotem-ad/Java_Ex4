package Algorithm;


public interface Action {
	
	String getName();
	
	State doAction(State state);
}

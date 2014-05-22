package model.algorithm;

import java.io.Serializable;



public interface Action extends Serializable {
	
	String getName();
	
	State doAction(State state);
}

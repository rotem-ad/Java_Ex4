package model.algorithm;

import java.io.Serializable;

import Entities.GameAction;



public interface Action extends Serializable {
	GameAction getId();
	String getName();
	State doAction(State state);
}

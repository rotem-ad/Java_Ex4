package model.game2048;

import Entities.GameAction;
import model.algorithm.State;

public class LeftAction2048 extends AbsAction2048 {

	@Override
	public String getName() {
		return "Left";
	}

	@Override
	public State doAction(State state) {
		this.boardSize=state.getBoardSize();
		this.currState=state.Clone();
		moveLeft();
		return this.currState;
	}

	@Override
	public GameAction getId() {
		// TODO Auto-generated method stub
		return GameAction.LEFT;
	}

}

package model.game2048;

import model.algorithm.State;

public class RightAction2048 extends AbsAction2048 {

	@Override
	public String getName() {
		return "Right";
	}

	@Override
	public State doAction(State state) {
		this.boardSize=state.getBoardSize();
		this.currState=state.Clone();
		moveRight();
		return this.currState;
	}

}

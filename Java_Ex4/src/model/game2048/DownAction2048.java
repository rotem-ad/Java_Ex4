package model.game2048;

import Entities.GameAction;
import model.algorithm.State;

public class DownAction2048 extends AbsAction2048 {

	@Override
	public String getName() {
		return "Down";
	}

	@Override
	public State doAction(State state) {
		this.boardSize=state.getBoardSize();
		this.currState=state.Clone();
		moveDown();
		return this.currState;
	}

	@Override
	public GameAction getId() {
		return GameAction.DOWN;
	}

}

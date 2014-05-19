package model.tictactoe;

import model.algorithm.Action;
import model.algorithm.State;

public class ActionTtt implements Action{

	private int row;
	private int col;
	private int action;
	

	
	public ActionTtt(int row, int col, int action) {
		super();
		this.row = row;
		this.col = col;
		this.action = action;
	}

	@Override
	public String getName() {
		return String.format("{0} at [{1},{2}]", action,row, col);
	}

	@Override
	public State doAction(State state) {
		State stt = state.Clone();
		stt.getBoard()[row][col]=action;
		return stt;
	}

}

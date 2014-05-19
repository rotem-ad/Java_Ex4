package model.algorithm;




public abstract class AbsModel implements Model {
	
	protected int boardSize;
	public State currState;
	
	
	public AbsModel(int boardSize) {
		
		this.boardSize = boardSize;
		this.currState = new State();
		this.currState.setBoard(new int[boardSize][boardSize]);
		for(int i=0; i<boardSize; i++)
			for(int j=0; j<boardSize; j++)
				this.currState.getBoard()[i][j] = 0;
		this.currState.setScore(0);
	}
	
}

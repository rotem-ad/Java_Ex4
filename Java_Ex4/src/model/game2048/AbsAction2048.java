package model.game2048;

import model.algorithm.Action;
import model.algorithm.State;

public abstract class AbsAction2048 implements Action{

	protected int boardSize;
	protected State currState;
	protected int EMPTY_CELL=0;

	
	

	
	
	public void moveUp() {
		boolean boardChanged = pushAllUp();
		for(int i=0; i< this.boardSize-1; i++)
		{
			for(int j=0; j<this.boardSize; j++)
			{
				//cell and previous cell are the same number --> add
				if (this.currState.getBoard()[i][j] == this.currState.getBoard()[i+1][j])
				{
					this.currState.getBoard()[i][j] += this.currState.getBoard()[i+1][j]; //add cells
					this.currState.setScore(this.currState.getScore() + this.currState.getBoard()[i+1][j]); //add to game score
					this.currState.getBoard()[i+1][j] = this.EMPTY_CELL;
					pushAllUp();
					boardChanged = pushAllUp() || boardChanged;
				}
			}
		}
		
	}

	public void moveDown() {
		boolean boardChanged = pushAllDown();
		for(int i=this.boardSize-1; i>0; i--)
		{
			for(int j=0; j<this.boardSize; j++)
			{
				//cell and next cell are the same number --> add
				if (this.currState.getBoard()[i][j] == this.currState.getBoard()[i-1][j])
				{
					this.currState.getBoard()[i][j] += this.currState.getBoard()[i-1][j]; //add cells
					this.currState.setScore(this.currState.getScore() + this.currState.getBoard()[i-1][j]); //add to game score
					this.currState.getBoard()[i-1][j] = this.EMPTY_CELL;
					boardChanged= pushAllDown() || boardChanged;
				}
			}
		}
		
	}
	
	public void moveLeft() {
		boolean boardChanged = pushAllLeft();
		for(int i=0; i<this.boardSize; i++)
		{
			for(int j=0; j<this.boardSize-1; j++)
			{
				//cell and previous cell are the same number --> add
				if (this.currState.getBoard()[i][j] == this.currState.getBoard()[i][j+1])
				{
					this.currState.getBoard()[i][j] += this.currState.getBoard()[i][j+1]; //add cells
					this.currState.setScore(this.currState.getScore() + this.currState.getBoard()[i][j+1]); //add to game score
					this.currState.getBoard()[i][j+1] = this.EMPTY_CELL;
					boardChanged = pushAllLeft() || boardChanged;
				}
			}
		}
		
		
	}

	public void moveRight() {
		boolean boardChanged = pushAllRigth();
		for(int i=0; i<this.boardSize; i++)
		{
			for(int j=this.boardSize-1; j>0; j--)
			{
				//cell and previous cell are the same number --> add
				if (this.currState.getBoard()[i][j] == this.currState.getBoard()[i][j-1])
				{
					this.currState.getBoard()[i][j] += this.currState.getBoard()[i][j-1]; //add cells
					this.currState.setScore(this.currState.getScore() + this.currState.getBoard()[i][j-1]); //add to game score
					this.currState.getBoard()[i][j-1] = this.EMPTY_CELL;
					boardChanged = pushAllRigth() || boardChanged ;
				}
			}
		}
		
		
	}
	
	private boolean pushAllRigth()
	{
		boolean boardChanged=false;
		for(int i=0; i<boardSize; i++)
		{
			for(int j=0; j<boardSize-1; j++)
			{
				if (currState.getBoard()[i][j+1] == EMPTY_CELL)
				{	
					currState.getBoard()[i][j+1] =currState.getBoard()[i][j];
					currState.getBoard()[i][j] = EMPTY_CELL;
					if(!boardChanged) 
						boardChanged=true;
				}
			}
		}
		return boardChanged;
	}
	
	private boolean pushAllLeft()
	{
		boolean boardChanged=false;
		for(int i=0; i<boardSize; i++)
		{
			for(int j=boardSize-1; j>0; j--)
			{
				if (currState.getBoard()[i][j-1] == EMPTY_CELL)
				{	
					currState.getBoard()[i][j-1] =currState.getBoard()[i][j];
					currState.getBoard()[i][j] = EMPTY_CELL;
					if(!boardChanged) 
						boardChanged=true;
				}
			}
		}
		return boardChanged;
	}
	
	private boolean pushAllUp()
	{
		boolean boardChanged=false;
		for(int i=boardSize-1; i>0; i--)
		{
			for(int j=0; j<boardSize; j++)
			{
				if (currState.getBoard()[i-1][j] == EMPTY_CELL)
				{	
					currState.getBoard()[i-1][j] =currState.getBoard()[i][j];
					currState.getBoard()[i][j] = EMPTY_CELL;
					if(!boardChanged) 
						boardChanged=true;
				}
			}
		}
		return boardChanged;
	}
	
	private boolean pushAllDown()
	{
		boolean boardChanged=false;
		for(int i=0; i<boardSize-1; i++)
		{
			for(int j=0; j<boardSize; j++)
			{
				if (currState.getBoard()[i+1][j] == EMPTY_CELL)
				{	
					currState.getBoard()[i+1][j] =currState.getBoard()[i][j];
					currState.getBoard()[i][j] = EMPTY_CELL;
					if(!boardChanged) 
						boardChanged=true;
				}
			}
		}
		return boardChanged;
	}


}

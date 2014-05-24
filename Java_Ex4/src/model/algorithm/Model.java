package model.algorithm;

import java.util.ArrayList;

public interface Model {
	// Methods
	/*public void moveUp();
	public void moveDown();
	public void moveLeft();
	public void moveRight();
	public int[][] getBoard();
	public int getScore();
	public void getPrevState(); //do actions to get to prev state
	public void saveGame(String path); //save game to xml file
	public void loadGame(String path);//load game from path. path must be xml file
	public void newGame(); //generate new game
	public State getCurrtState();
	public void initBoard();
	public void moveUpRight();
	public void moveUpLeft();
	public void moveDownRight();
	public void moveDownLeft();*/
	public boolean isGameWon(State state);
	public boolean isGameOver(State state); //is given state concludes the game
	ArrayList<Action> getPossibleActions(State state, int player); //return possible actions resolving from given state
    /**
     * Calculates a heuristic variance-like score that measures how clustered the
     * board is.
     * 
     * @param boardArray
     * @return 
     */
    public int calculateClusteringScore(int[][] boardArray);
    
    /**
     * Estimates a heuristic score by taking into account the real score, the
     * number of empty cells and the clustering score of the board.
     * 
     * @param actualScore
     * @param numberOfEmptyCells
     * @param clusteringScore
     * @return 
     */
    public  int heuristicScore(int actualScore, int numberOfEmptyCells, int clusteringScore);

}

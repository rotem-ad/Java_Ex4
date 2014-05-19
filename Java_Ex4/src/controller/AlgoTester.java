package controller;

import model.algorithm.Action;
import model.algorithm.MiniMax;
import model.algorithm.Solver;
import model.algorithm.State;
import model.game2048.*;


public class AlgoTester {

	public static void main(String[] args) {
		 doStuff();
	}
	public static void doStuff()
	{
		Model2048 m2048 = new Model2048(4);
		m2048.currState.getBoard()[0][1]=2;
		m2048.currState.getBoard()[0][2]=4;
		
		Solver solver = new MiniMax(m2048, 3);
		State s;
		s = m2048.currState.Clone();
		for(int i=0; i<200; i++)
		{
			Action nextMove = solver.Solve(s);
			int evaluatedNodes =solver.getNumOfEvaluetedNodes();
			System.out.println(String.format("%s evaluated nodes:%s",  nextMove.getName(), ""+evaluatedNodes) );
			
			s = nextMove.doAction(s);
		}
		
		
	}

}

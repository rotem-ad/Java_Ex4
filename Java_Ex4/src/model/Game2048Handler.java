package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.algorithm.Action;
import model.algorithm.MiniMax;
import model.algorithm.Solver;
import model.algorithm.State;
import model.game2048.Model2048;

public class Game2048Handler implements ClientHandler{
	// Members
	Solver solver;
	int treeDepth;
	
	public Game2048Handler() {
		//TODO: get values from outside
		Model2048 m2048 = new Model2048(4);
		this.treeDepth = 3;
		solver = new MiniMax(m2048, this.treeDepth);
	}

	@Override
	public void handleClient(ObjectInputStream inFromClient,ObjectOutputStream out2Client) {
		Action nextMove = null;
		try {
		//TODO: change stop condition
		while (nextMove != null) {
				// Get state from client
				State state = (State) inFromClient.readObject();

				// Handle the client's request - solve given state
				nextMove = solver.Solve(state);
				System.out.println("solving..");
				
				// Send solution to client 
				out2Client.writeObject(nextMove);
				out2Client.flush();
			}
		
		System.out.println("Handler 2048 finished");
		inFromClient.close();
		out2Client.close();
		//client.close();

		} catch (ClassNotFoundException | IOException  e) {
			e.printStackTrace();
		}
	}

}

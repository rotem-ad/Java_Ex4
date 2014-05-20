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
	
	// Game2048Handler constructor
	public Game2048Handler(Solver solver) {
		this.solver = solver;
	}

	@Override
	public void handleClient(ObjectInputStream inFromClient,ObjectOutputStream out2Client) {
		Action nextMove = null;
		try {
		State state;
		// TODO: change stop condition?
		// Get state from client. If score is -1 then finish handle this client
		while ((state = (State) inFromClient.readObject()).getScore() != -1) {
				// Handle the client's request - solve given state
				nextMove = solver.Solve(state);
				System.out.println(Thread.currentThread().getName() + ": solving..");
				
				// Send solution to client 
				out2Client.writeObject(nextMove);
				out2Client.flush();
			} 
		
		System.out.println("Handler 2048 finished");
		inFromClient.close();
		out2Client.close();
		//client.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("Client disconnected unexpectedly");
			return;
		}
	}

}

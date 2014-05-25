package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import model.algorithm.Solver;
import model.algorithm.State;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Entities.Game2048Settings;
import Entities.GameAction;
import Entities.SlimState;
/**
 * @author ROTEM-A
 * 
 * Handler for clients which runs 2048 game
 */
public class Game2048Handler implements ClientHandler{
	// Members
	private Solver solver;
	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * Game2048Handler constructor
	 * 
	 * @param solver an object of class that implements Solver interface
	 */
	public Game2048Handler(Solver solver) {
		this.solver = solver;
	}

	@Override
	public void handleClient(ObjectInputStream inFromClient,ObjectOutputStream out2Client) {
		GameAction nextMove = null;
		try {
		SlimState slimstate;
		logger.info("Starting handling client");
		System.out.println("Starting handling client");
		// Get state from client. If score is -1 then finish handle this client
		while ((slimstate = (SlimState) inFromClient.readObject()).getScore() != Game2048Settings.FLAG_STOP_SOLVING) {
				// Handle the client's request - solve given state
				nextMove = solver.Solve(State.fromSlimState(slimstate));
				
				// Send solution to client 
				out2Client.writeObject(nextMove);
				out2Client.flush();
			} 
		
		System.out.println("Handler 2048 finished");
		logger.info("Finished handling client");
		inFromClient.close();
		out2Client.close();
		//client.close();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			Date currDate = new Date();
			logger.error("Client disconnected unexpectedly");
			System.out.println(""+currDate + "  Client disconnected unexpectedly");
			return;
		}
	}

}

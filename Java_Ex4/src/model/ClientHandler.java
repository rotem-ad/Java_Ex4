package model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.algorithm.Solver;

public interface ClientHandler {
	
	public void handleClient(ObjectInputStream inFromClient, ObjectOutputStream out2Client);

}

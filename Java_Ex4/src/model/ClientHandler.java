package model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author ROTEM-A
 * 
 * Interface for handling different types of clients (2048, maze game, etc. )
 */
public interface ClientHandler {
	
	/**
	 * Method for handling different types of clients according to defined protocol
	 * 
	 * @param inFromClient client's object input stream
	 * @param out2Client client's object output stream
	 */
	public void handleClient(ObjectInputStream inFromClient, ObjectOutputStream out2Client);

}

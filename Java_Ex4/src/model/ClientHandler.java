package model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Interface for handling different types of clients (2048, maze game, etc. )
 */
public interface ClientHandler {
	
	/**
	 * @author ROTEM-A
	 * 
	 * @param inFromClient client's object input stream
	 * @param out2Client client's object output stream
	 */
	public void handleClient(ObjectInputStream inFromClient, ObjectOutputStream out2Client);

}

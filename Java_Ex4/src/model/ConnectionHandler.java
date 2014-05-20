package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ConnectionHandler implements ClientHandler {
	
	// Data Members
	private static Integer val = 0;
	
	// Constructor

	//@Override
	public void handleClient(ObjectInputStream inFromClient, ObjectOutputStream out2Client) {
		String line;
		try {
			while (!(line=(String)inFromClient.readObject()).equals("exit")) {
				System.out.println(line);
			}
			return;
		} catch (IOException e) {
			System.out.println("Connection disconnected unexpectedly");
			//e.printStackTrace();
		}
		catch (Exception e) {
			// When inFromClient is NULL
			System.out.println("ConnectionHandler finished");
		}
		return;
	}

}
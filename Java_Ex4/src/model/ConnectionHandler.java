package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ConnectionHandler implements ClientHandler{

	@Override
	public void handleClient(ObjectInputStream inFromClient, ObjectOutputStream out2Client) {
		String line;
		try {
			while (!(line=(String)inFromClient.readObject()).equals("exit")) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			// When inFromClient is NULL
			System.out.println("Handler finished");
		}
		
	}

}
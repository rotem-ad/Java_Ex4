package model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface ClientHandler <T> {
	
	public T handleClient(ObjectInputStream inFromClient, ObjectOutputStream out2Client);

}

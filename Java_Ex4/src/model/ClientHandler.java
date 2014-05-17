package model;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface ClientHandler {
	
	public void handleClient(BufferedReader inFromClinet, PrintWriter out2Client);

}

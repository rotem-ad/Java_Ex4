package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class RunClient {

	public static void main(String[] srgs) throws Exception 
	{
		Socket  myServer = new Socket(InetAddress.getLocalHost(), 5000);
		
		//ObjectInputStream inFromServer = new ObjectInputStream(myServer.getInputStream()); // This line caused a strange BUG!
		ObjectOutputStream outToServer = new ObjectOutputStream(myServer.getOutputStream());
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		String line;
		//read input
		while(!(line=inFromUser.readLine()).equals("exit"))
		{
			outToServer.writeObject(line); // Send object to server output stream
			outToServer.flush();
		}
		outToServer.writeObject(line);
		outToServer.flush();
		
		//close all stuff
		inFromUser.close();
		outToServer.close();
		myServer.close();
	}
}

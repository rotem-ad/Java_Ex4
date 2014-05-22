package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import model.algorithm.Action;
import model.algorithm.State;
import model.game2048.Model2048;

public class RunClient {

	public static void main(String[] srgs) throws Exception 
	{
		Socket  myServer = new Socket(InetAddress.getByName("10.0.0.9"), 5550);
		
		ObjectOutputStream outToServer = new ObjectOutputStream(myServer.getOutputStream());
		ObjectInputStream inFromServer = new ObjectInputStream(myServer.getInputStream());				
		
		Model2048 m2048 = new Model2048(4);
		m2048.currState.getBoard()[0][1]=2;
		m2048.currState.getBoard()[0][2]=4;
		
		State s;
		s = m2048.currState.Clone();
		
		for(int i=0; i<10; i++)
		{
			outToServer.writeObject(s);
			outToServer.flush();
			
			Action nextMove = null;
			
			nextMove = (Action) inFromServer.readObject();
			
			System.out.println("Server returned: " + nextMove.getName());
			
			s = nextMove.doAction(s);
		}
		
		// Send state with -1 score to end communication with server
		s.setScore(-1);
		outToServer.writeObject(s);
		outToServer.flush();
		
		System.out.println("Client finished");
		
		//close resources
		outToServer.close();
		myServer.close();
	}
}

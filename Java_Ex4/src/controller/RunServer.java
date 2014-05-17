package controller;

import model.ClientHandler;
import model.ConnectionHandler;
import model.Server;

public class RunServer {

	public static void main(String[] args) {
		ClientHandler ch = new ConnectionHandler(); 
		
		Server srv = new Server(5000, 3, ch);
	
		new Thread(srv).start();

		/*try {
		    Thread.sleep(20 * 1000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}*/
		
		//TODO: find elegant way to stop server
		//System.out.println("Stopping Server");
		//srv.stopServer();
	}

}

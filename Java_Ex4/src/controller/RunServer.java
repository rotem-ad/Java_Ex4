package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import model.ClientHandler;
import model.Server;

import com.thoughtworks.xstream.XStream;

import config.ServerConf;

/**
 * @author ROTEM-A
 *
 * Run class for controlling the server instance
 */
public class RunServer {

	// Constants
	private static final String SERVER_CONF_PATH = "resources/ConfigFiles/serverConf.xml";
	
	/**
	 * Main method for server control:
	 * 1. Loads server configuration from serverConf.xml and starts server
	 * 2. Provides "shutdown" command for stopping the server instance
	 * @param args
	 */
	public static void main(String[] args) {
		XStream xs = new XStream();
		
		BufferedReader xmlInput = null;
		try {
			xmlInput = new BufferedReader(new FileReader(SERVER_CONF_PATH));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Load server configuration settings from XML file
		ServerConf srvConfig = (ServerConf) xs.fromXML(xmlInput);
		
		// Close xml file
		try {
			xmlInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ClientHandler handler = srvConfig.getHandler();
		int port = srvConfig.getPort();
		int poolSize = srvConfig.getPoolSize();
		
		// Initialize server instance
		Server srv = new Server(port,poolSize,handler);
	
		new Thread(srv).start(); // Invoke server's run method in new thread 
		
		// Type "shutdown" command to stop server
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		try {
			String userCommand;
			while ( !((userCommand = inFromUser.readLine()).equals("shutdown")) ) {
					Thread.sleep(5000);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Stopping Server..");
		srv.stopServer(); // Stop the server instance

	}

}

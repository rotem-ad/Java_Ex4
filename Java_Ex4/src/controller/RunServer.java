package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import model.ClientHandler;
import model.Server;

import com.thoughtworks.xstream.XStream;

import config.ServerConf;

public class RunServer {

	// Constants
	private static final String SERVER_CONF_PATH = "resources/ConfigFiles/serverConf.xml";
	
	
	public static void main(String[] args) {
		
		
		/*XStream xs = new XStream();
		ServerConf srvConf = new ServerConf(5000, 8, new ConnectionHandler());

		String xmlString = xs.toXML(srvConf);
		
		BufferedWriter xmlOut = null;
		try {
			xmlOut = new BufferedWriter(new FileWriter("resources/ConfigFiles/serverConf.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			xmlOut.write(xmlString);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Close xml file
		try {
			xmlOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		
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

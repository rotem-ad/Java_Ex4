package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server implements Runnable {
	// Data Members
	private ServerSocket server; 
	private int port;      // listen port
	private int poolSize;  // size of thread pool
	private boolean stop;  // flag to stop the server
	private ClientHandler handler; // handler for client requests
	
	private static final Logger logger = LogManager.getLogger();
	
	// Methods
	
	// Server constructor
	public Server(int port, int poolSize, ClientHandler handler) {
		this.port = port;
		this.poolSize = poolSize;
		this.handler = handler;
		this.stop = false;
	}
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(port);
			
			String infoMessage = "Server started";
			System.out.println(infoMessage);
			
			logger.info(infoMessage);
			
			// Write message also to log file
			//writeToLog(infoMessage);
			
			// Server timeout
			server.setSoTimeout(1000);
			
			// Thread pool creation 
			ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);
			
			// Server main loop
			while (!stop) {
				try {
					// Wait for client request
					final Socket client = server.accept();
					
					// Set proper streams for communication
					final ObjectInputStream inFromClient = new ObjectInputStream(client.getInputStream());
					final ObjectOutputStream out2Client = new ObjectOutputStream(client.getOutputStream());

					// Allocate separate thread for the client and handle the client's requests
					threadPool.execute(new Runnable() {

						@Override
						public void run() {
							//writeToLog("Client connected. Details: " + "IP: " + client.getInetAddress() + " Class: " + client.getClass());
							//writeToLog("Handling thread is: " + Thread.currentThread().getName());
							// Handle client given state
							handler.handleClient(inFromClient, out2Client);
							try {
								//writeToLog("Closing connection with client: " + "IP: " + client.getInetAddress() + " Class: " + client.getClass());
								client.close();
							} catch (IOException e) {
								e.printStackTrace();
							}

						}
					});

				} catch (SocketTimeoutException e) {
					//System.out.println("Connection timed out");
				}
			}
			
			// When stop is TRUE, terminate the server
			//writeToLog("Stopping server......"); // write to log file
			threadPool.shutdownNow(); // shutdown thread pool
			server.close(); // close server socket
			
			infoMessage = "Server stopped successfuly";
			System.out.println(infoMessage);
			logger.info(infoMessage);
			
		} catch (IOException e) {
			// For example: connection unexpectedly disconnected
			e.printStackTrace();
		}

	}
	
	public void stopServer() {
		// Setting stop flag to TRUE
		this.stop = true;
	}

	public int getPort() {
		return port;
	}

	public int getPoolSize() {
		return poolSize;
	}
	
	// Method for writing informational messages to server's log file
	protected static void writeToLog(String message) {
		Date currDate = new Date();
		try {
			BufferedWriter logFile = new BufferedWriter(new FileWriter("resources/Log/Server.log"));
			logFile.append(""+currDate + "\t"); // write current date and time
			logFile.append(message + "\n"); // write given message to file
			logFile.flush();
			logFile.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.UUID;
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
	private static final Logger logger = LogManager.getLogger(); // server logger for writing to log file
	
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
			
			// Server timeout
			server.setSoTimeout(1000);
			
			// Thread pool creation 
			ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);
			
			// Server main loop
			while (!stop) {
				try {
					// Wait for client request
					final Socket client = server.accept();
					System.out.println("client connected");
					// Set proper streams for communication
					final ObjectInputStream inFromClient = new ObjectInputStream(client.getInputStream());
					final ObjectOutputStream out2Client = new ObjectOutputStream(client.getOutputStream());

					// Allocate separate thread for the client and handle the client's requests
					threadPool.execute(new Runnable() {

						@Override
						public void run() {
							// TODO: pass UUID to handler
							String clientId = UUID.randomUUID().toString();
							String clientIp = (client.getInetAddress().toString().substring(1));
							logger.info("Client connected.\nClient details: " + "IP: " + clientIp + " ID: " + clientId);
							// Handle client given state
							handler.handleClient(inFromClient, out2Client);
							try {
								logger.info("Closing connection with client:\nClient details: " + "IP: " + clientIp + " ID: " + clientId);
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
			logger.info("Stopping server......"); // write to log file
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

}

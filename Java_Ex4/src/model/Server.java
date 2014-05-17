package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
	// Data Members
	ServerSocket server;
	private int port;
	private int poolSize;
	private ClientHandler handler;
	private boolean stop;
	
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
			System.out.println("Server started");
			// Server timeout
			server.setSoTimeout(1000);
			
			// Thread pool creation 
			ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);
			
			// Server main loop
			while (!stop) {
				try {
				//System.out.println("Waiting for client");
				final Socket client = server.accept();
				System.out.println("Client connected");
				
				/*final BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
				final PrintWriter out2Client = new PrintWriter(new OutputStreamWriter(client.getOutputStream())); */
				
				final ObjectInputStream inFromClient = new ObjectInputStream(client.getInputStream());
				final ObjectOutputStream out2Client = new ObjectOutputStream(client.getOutputStream());
				
				threadPool.execute(new Runnable() {
					
					@Override
					public void run() {
						// Handle the client's request
						handler.handleClient(inFromClient, out2Client);
						try {
							inFromClient.close();
							out2Client.close();
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
			threadPool.shutdownNow();
			server.close();
			System.out.println("Server terminated");
			
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

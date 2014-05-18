package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Server implements Runnable {
	// Data Members
	ServerSocket server;
	private int port;
	private int poolSize;
	private ClientHandler<Integer> handler;
	private boolean stop;
	
	// Methods
	
	// Server constructor
	public Server(int port, int poolSize, ClientHandler<Integer> handler) {
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
				
				Future<Integer> retVal = threadPool.submit(new Callable<Integer>() {

					@Override
					public Integer call() throws Exception {
						// Handle the client's request
						Integer val = (Integer) handler.handleClient(inFromClient, out2Client);
						try {
							inFromClient.close();
							out2Client.close();
							client.close();
							
						} catch (IOException e) {
							e.printStackTrace();
						}
						return val;
					}
					
				});
				
				try {
					System.out.println("Client returned: " + retVal.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

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

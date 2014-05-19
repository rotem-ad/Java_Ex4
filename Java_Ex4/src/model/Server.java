package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import model.algorithm.Action;
import model.algorithm.Solver;
import model.algorithm.State;

public class Server implements Runnable {
	// Data Members
	ServerSocket server;
	private int port;
	private int poolSize;
	private boolean stop;
	private Solver solver;
	//private ClientHandler<Integer> handler;
	
	// Methods
	
	// Server constructor
	public Server(int port, int poolSize, Solver solver) {
		this.port = port;
		this.poolSize = poolSize;
		this.solver = solver;
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
			
			//create a list to hold the Future object associated with Callable
	        List<Future<Action>> list = new CopyOnWriteArrayList<Future<Action>>();
			
			// Server main loop
			while (!stop) {
				try {
					// Check if any task is done 
					for(Future<Action> fut : list){
			            try {
			                //print the return value of Future, notice the output delay in console
			                // because Future.get() waits for task to get completed
			            	if (fut.isDone()) {
			            		System.out.println(new Date()+ "::"+fut.get().getName()); // Prints each Action's name
			            		list.remove(fut);
			            	}
			            } catch (InterruptedException | ExecutionException e) {
			                e.printStackTrace();
			            }
			        }	
					
					
				//System.out.println("Waiting for client");
				final Socket client = server.accept();
				System.out.println("Client connected");
				
				/*final BufferedReader inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
				final PrintWriter out2Client = new PrintWriter(new OutputStreamWriter(client.getOutputStream())); */
				
				final ObjectInputStream inFromClient = new ObjectInputStream(client.getInputStream());
				final ObjectOutputStream out2Client = new ObjectOutputStream(client.getOutputStream());
				
				
				//final State start = (State) inFromClient.readObject();
				
				//TODO: maybe use FutureTask?
				 Future<Action> task = threadPool.submit(new Callable<Action>() {

					@Override
					public Action call() throws Exception {
						//return the thread name executing this callable task
				        //return Thread.currentThread().getName();
						
						Action nextMove = null;
						try {
						while (!client.isClosed()) {
						
						State start = (State) inFromClient.readObject();
						
						// Handle the client's request - solve given state
						nextMove = solver.Solve(start);
						System.out.println("solving..");	
						
							out2Client.writeObject(nextMove);
							out2Client.flush();
							//inFromClient.close();
							//out2Client.close();
							//client.close();
						}
							
						} catch (IOException e) {
							//e.printStackTrace();
							client.close();
						}
						// TODO: return default Action?
						return nextMove;
						}		
				});
				 
				// Add task to list of Futures 
				list.add(task);


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

package config;

import model.ClientHandler;

public class ServerConf {
	// Data Members
	private int port;
	private int poolSize;
	private ClientHandler handler;
	
	// ServerConf constructor
	public ServerConf(int port, int poolSize, ClientHandler handler){
		this.port = port;
		this.poolSize = poolSize;
		this.handler = handler;
	}

	public int getPort() {
		return port;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public ClientHandler getHandler() {
		return handler;
	}
	
}

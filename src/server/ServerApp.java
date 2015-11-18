package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.Constant;

/**
 * This is the entry point for server application.
 * 
 * The functions include:
 * 1. Create an instance of the ServerCtrl class
 * 2. Register this at RMI registry so clients can find and use it
 * 
 * @author praveen
 */

public class ServerApp {
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException{
		// Bind the server control object at the RMI registry
		ServerCtrl serverCtrl = new ServerCtrl();
		Registry registry = LocateRegistry.createRegistry(Constant.RMI_PORT);
		registry.bind(Constant.RMI_ID, serverCtrl);
		System.out.println("Server started!");
	}
	
}

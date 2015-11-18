package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.Constant;
import common.IGameServer;

/**
 * This is the entry point for client application.
 * 
 * Functions: <br/>
 * 1. Lookup and fetch ServerCtrl from Registry <br/>
 * 2. Create an instance of the ClientCtrl for later use <br/>
 * 3. Create an instance of ClientView and take username <br/>
 * 4. Use the above inside inside ClientView to login using ClientCtrl <br/>
 * 5. Rest of the game
 * 
 * @author praveen
 */
public class ClientApp {

	public static void main(String[] args) throws RemoteException, NotBoundException {
		// Get server control object from registry
		Registry registry = LocateRegistry.getRegistry("localhost", Constant.RMI_PORT);
		IGameServer server = (IGameServer) registry.lookup(Constant.RMI_ID);

		// Initiate the client controller
		ClientCtrl clientCtrl = new ClientCtrl(server);

		// Initiate the view - GUI, of the client and the game starts, NOW!
		new ClientView(clientCtrl);
	}

}

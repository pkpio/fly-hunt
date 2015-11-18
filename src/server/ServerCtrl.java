package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import common.IGameClient;
import common.IGameServer;

/**
 * The implementation of the server stud exposed to the clients. An instance of this object should be
 * be registered at the Registry so that clients can find and use it.
 * 
 * @author praveen
 */
public class ServerCtrl extends UnicastRemoteObject implements IGameServer{

	protected ServerCtrl() throws RemoteException {
		super();
	}

	@Override
	public void login(String playerName, IGameClient client) throws RemoteException {
		client.recieveFlyPosition(22, 23);
		// TODO Auto-generated method stub
	}

	@Override
	public void logout(String playerName) throws RemoteException {
		// TODO Auto-generated method stub
	}

	@Override
	public void huntFly(String playerName) throws RemoteException {
		// TODO Auto-generated method stub
	}

}

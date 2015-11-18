package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

import common.IGameClient;
import common.IGameServer;

/**
 * The implementation of the server stud exposed to the clients. An instance of
 * this object should be be registered at the Registry so that clients can find
 * and use it.
 * 
 * @author praveen
 */
public class ServerCtrl extends UnicastRemoteObject implements IGameServer {
	// Fly's state info
	int mFlyPosX;
	int mFlyPosY;

	// Clients state info
	HashMap<String, IGameClient> mClients = new HashMap<String, IGameClient>();
	HashMap<String, Integer> mClientScores = new HashMap<String, Integer>();

	protected ServerCtrl() throws RemoteException {
		super();

		// Setup initial fly coordinates
		updateFlyPosition();
	}

	@Override
	public void login(String playerName, IGameClient client) throws RemoteException {
		mClients.put(playerName, client);

		// Notify new client about the current positions of the fly
		client.recieveFlyPosition(mFlyPosX, mFlyPosY);
	}

	@Override
	public void logout(String playerName) throws RemoteException {
		mClients.remove(playerName);
		mClientScores.remove(playerName);
	}

	@Override
	public void huntFly(String playerName) throws RemoteException {
		// Update score of the client
		mClientScores.put(playerName, mClientScores.get(playerName) + 1);

		// Notify clients about new fly coordinates
		updateFlyPosition();

		// Notify clients about updated scores
		updateScores();
	}

	/**
	 * Generates new position for the Fly and distributes them to all the
	 * clients
	 * 
	 * @throws RemoteException
	 */
	void updateFlyPosition() throws RemoteException {
		// Initialize a fly position for starters
		if (mClients == null) {
			mFlyPosX = 12;
			mFlyPosY = 12;
			return;
		}

		// Generate new coordinates for Fly
		mFlyPosX = 23;
		mFlyPosY = 23;

		// Send the position to all clients
		for (IGameClient client : mClients.values())
			client.recieveFlyPosition(mFlyPosX, mFlyPosY);
	}
	
	/**
	 * Distributes new scores to all the clients
	 * 
	 * @throws RemoteException
	 */
	void updateScores() throws RemoteException {
		if(mClientScores == null)
			return;
		
		// Build scores array
		String[] playerNames = new String[mClientScores.size()];
		int[] scores = new int[mClientScores.size()];
		int pos = 0;
		for (String playerName : mClientScores.keySet()){
			playerNames[pos] = playerName;
			scores[pos] = mClientScores.get(playerName);
			pos++;
		}
		

		// Send the scores to all clients
		for (IGameClient client : mClients.values())
			client.recieveFlyHunted(playerNames, scores);
	}

}

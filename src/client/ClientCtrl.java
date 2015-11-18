package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.Listeners.IFlyPositionUpdate;
import client.Listeners.IPlayerPointsUpdate;
import common.IGameClient;
import common.IGameServer;

/**
 * Implementation of client controls which includes those functions exposed by
 * IGameClient and other methods that are required for view<-->controller
 * communication
 * 
 * @author praveen
 */
public class ClientCtrl extends UnicastRemoteObject implements IGameClient {
	private static final long serialVersionUID = 3982440795885854423L;

	IGameServer mServer;
	String mPlayerName;
	int mFlyPosX;
	int mFlyPosY;

	// Subscriptions
	IFlyPositionUpdate mFlyPositionListener;
	IPlayerPointsUpdate mPointsUpdateListener;

	/**
	 * Creates an instance of the Client controller. This implements IGameClient
	 * that will be used at login.
	 * 
	 * @param server
	 *            An instance of the IGameServer object for RPCs between client
	 *            and server
	 */
	protected ClientCtrl(IGameServer server) throws RemoteException {
		super();
		this.mServer = server;
	}

	/**
	 * Called by the server to notify that fly on UI is hunted - either by the
	 * current user or someone else. The call back should behave identically in
	 * both cases
	 * 
	 * @param playerNames
	 *            String array containing names of the players
	 * @param newPoints
	 *            int array containing scores of the players
	 * @throws RemoteException
	 */
	@Override
	public void recieveFlyHunted(String[] playerNames, int[] newPoints) throws RemoteException {
		if (mPointsUpdateListener != null)
			mPointsUpdateListener.onPlayerPointsUpdate(playerNames, newPoints);
	}

	@Override
	/**
	 * called by the server to notify about the new fly position - because the
	 * one on the UI has been hunted by someone
	 * 
	 * @param x
	 *            New x-position of the fly
	 * @param y
	 *            New y-position of the fly
	 * @throws RemoteException
	 */
	public void recieveFlyPosition(int x, int y) throws RemoteException {
		System.out.println("New fly poisitions. X : " + x + " Y : " + y);
		mFlyPosX = x;
		mFlyPosY = y;
		if (mFlyPositionListener != null)
			mFlyPositionListener.onFlyPositionUpdate(x, y);
	}

	/**
	 * Login with given playerName.
	 * 
	 * @param playerName
	 *            Player name. Will be persistent inside the controller.
	 * @throws RemoteException
	 */
	public void login(String playerName) throws RemoteException {
		this.mPlayerName = playerName;
		mServer.login(playerName, this);
	}

	/**
	 * Logout player.
	 * 
	 * @param none
	 *            Player name. Will be persistent inside the controller.
	 * @throws RemoteException
	 */
	public void logOut() throws RemoteException {
		mServer.logout(this.mPlayerName);
	}

	/**
	 * The current fly has been hunted by the user of this client
	 * 
	 * @throws RemoteException
	 */
	public void huntFly() throws RemoteException {
		mServer.huntFly(mPlayerName);
	}

	/**
	 * Subscribe for updates to Fly's position
	 * 
	 * @param posListener
	 */
	public void setFlyPositionUpdateListener(IFlyPositionUpdate posListener) {
		this.mFlyPositionListener = posListener;
	}

	/**
	 * Subscribe for updates to scores
	 * 
	 * @param pointsListener
	 */
	public void setPlayerPointsUpdateListener(IPlayerPointsUpdate pointsListener) {
		this.mPointsUpdateListener = pointsListener;
	}

	/**
	 * Notifies controller that a new game starts. This would also trigger a UI
	 * refresh for fly position so, initiate the UI & event listeners before
	 * this call.
	 */
	public void startGame() {
		System.out.println("Game started on client");
		if (mFlyPositionListener != null)
			mFlyPositionListener.onFlyPositionUpdate(mFlyPosX, mFlyPosY);
	}

}

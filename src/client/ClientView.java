package client;

import java.rmi.RemoteException;

/**
 * The client GUI logic appears here
 * 
 * 
 * @author praveen
 */
public class ClientView implements Listeners.IFlyPositionUpdate, Listeners.IPlayerPointsUpdate{
	ClientApp mApp;
	ClientCtrl mController;

	/**
	 * Creates an instance of the Client view.
	 * 
	 * @param app
	 *            A reference to the application object
	 * @param controller
	 *            A reference to the client controller
	 */
	public ClientView(ClientApp app, ClientCtrl controller) throws RemoteException {
		this.mApp = app;
		this.mController = controller;
		initGUI();
	}

	private void initGUI() throws RemoteException {
		// 1. -TODO- Init gui here

		// 2. -TODO- Collect playerName

		// 3. -TODO- login
		mController.login("");

		// 4. Subscribe for Fly position & score updates
		mController.setFlyPositionUpdateListener(this);
		mController.setPlayerPointsUpdateListener(this);
		
		// 5. Run the game. Look for more functions in Client controller
		// Note : View doesn't have access to server stubs and doesn't maintain any
		// game state info except those required for UI display

	}
	
	@Override
	public void onFlyPositionUpdate(int posX, int posY){
		//-TODO-
	}
	
	@Override
	public void onPlayerPointsUpdate(String[] playerNames, int[] scores){
		// -TODO-
	}

}

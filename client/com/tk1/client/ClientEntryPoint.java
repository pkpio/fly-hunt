package com.tk1.client;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

import com.tk1.player.Player;

public class ClientEntryPoint {

	private static final long serialVersionUID = 1L;

	protected ClientEntryPoint() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	private Player playerObj;
	private GameFrame clientViewObj;

	void initGame(String port) throws RemoteException {
		clientViewObj = new GameFrame();
		playerObj = new Player();
		clientViewObj.setLoginUI();
		clientViewObj.addListener(new ClientViewEvent() {
			@Override
			public void flieHunted() {

			}

			@Override
			public void playerLogin(String name) {

				boolean status;
				playerObj.setPlayerName(name);
				try {
					status = true;// addServerIntf.login(name);
					if (status) {
						clientViewObj.init();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void playerLogoff() {
			}

		});

	}

	public static void main(String[] args) throws RemoteException {
		new ClientEntryPoint().initGame("");

	}


	public void updatePlayerInfo(ArrayList<Player> playerList) {

		String playerListString = new String("Hello  " + playerObj.getPlayerName() + "\n\n");
		playerListString += "Player   \tScore" + "\n";
		for (Player P : playerList) {
			playerListString += P.getPlayerName() + "\t" + String.valueOf(P.getPoints()) + "\n";
		}
		clientViewObj.updatePlayerList(playerListString);
	}

}
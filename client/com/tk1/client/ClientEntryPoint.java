package com.tk1.client;

import java.rmi.RemoteException;
import java.util.ArrayList;

import com.tk1.player.Player;

public class ClientEntryPoint {
	private Player player;
	private GameFrame gameFrame;

	protected ClientEntryPoint() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws RemoteException {
		new ClientEntryPoint().initGame("");

	}

	void initGame(String port) throws RemoteException {
		gameFrame = new GameFrame();
		player = new Player();
		gameFrame.setLoginUI();
		gameFrame.addListener(new ClientViewEvent() {
			@Override
			public void flieHunted() {

			}

			@Override
			public void playerLogin(String name) {

				boolean status;
				player.setPlayerName(name);
				try {
					status = true;// addServerIntf.login(name);
					if (status) {
						gameFrame.init();
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

	public void updatePlayerInfo(ArrayList<Player> playerList) {

		String playerListString = new String("Hello  " + player.getPlayerName() + "\n\n");
		playerListString += "Player   \tScore" + "\n";
		for (Player P : playerList) {
			playerListString += P.getPlayerName() + "\t" + String.valueOf(P.getPoints()) + "\n";
		}
		gameFrame.updatePlayerList(playerListString);
	}

}
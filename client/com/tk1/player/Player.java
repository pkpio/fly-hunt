package com.tk1.player;
import java.io.Serializable;

public class Player implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int points;
	private String playerName;
	
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public int getPoints() {
	
		return points;
	}
	public void setPoints(int in_points) {
		
		this.points = in_points;
	}
}

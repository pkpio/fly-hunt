package com.tk1.client;
import java.io.Serializable;

public class Fly implements Serializable{

	private static final long serialVersionUID = 1L;
	private int positionX,positionY;
	private boolean flyHunted;
	
	
	
	 boolean isFlyHunted() {
		return flyHunted;
	}
	 void setFlyHunted(boolean hunted) {
		this.flyHunted = hunted;
	}
	 int getX() {
		
		return positionX;
	}
	 int getY() {
		return positionY;
	}
	 void setX(int x) {
	
		this.positionX = x;
		
	}
	 void setY(int y) {
		
		this.positionY = y;
	}

}

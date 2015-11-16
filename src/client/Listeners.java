package src.client;

public class Listeners {
	
	/**
	 * Client view should implement this to subscribe for Fly position updates from controller
	 * 
	 * @author praveen
	 *
	 */
	public interface IFlyPositionUpdate {
		public void onFlyPositionUpdate(int posX, int posY);		
	}
	
	/**
	 * Client view should implement this to subscribe for points updates
	 * 
	 * @author praveen
	 *
	 */
	public interface IPlayerPointsUpdate {
		public void onPlayerPointsUpdate(String[] playerNames, int[] scores);		
	}
	
	
}

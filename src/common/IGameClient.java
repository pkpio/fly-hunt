package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGameClient extends Remote{
	void recieveFlyHunted(String[] playerNames, int[] newPoints) throws RemoteException;
	void recieveFlyPosition(int x, int y) throws RemoteException;
}

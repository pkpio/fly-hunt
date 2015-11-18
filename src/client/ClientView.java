package client;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

/**
 * The client GUI logic appears here
 * 
 * 
 * @author praveen
 */
public class ClientView implements Listeners.IFlyPositionUpdate, Listeners.IPlayerPointsUpdate {
	ClientCtrl mController;
	private JFrame gameFrame;
	private JTextArea playersListTextArea;
	private JLabel flyImage;
	private JPanel playAreaPanel;
	private JPanel playerListPanel;
	private GroupLayout groupLayout;
	private JLabel playerNameLabel;
	private JButton connectButton;
	private JTextField playerNameTextField;

	/**
	 * Creates an instance of the Client view.
	 * 
	 * @param app
	 *            A reference to the application object
	 * @param controller
	 *            A reference to the client controller
	 */
	public ClientView(ClientCtrl controller) throws RemoteException {
		this.mController = controller;
		initGUI();
	}

	private void initGUI() throws RemoteException {
		// 1. -TODO- Init gui here
		gameFrame = new JFrame("Fly Hunt");
		gameFrame.setSize(607, 447);
		int xCoordinate, yCoordinate;
		xCoordinate = (int) gameFrame.getSize().getWidth() / 2;
		yCoordinate = (int) gameFrame.getSize().getHeight() / 2;
		playAreaPanel = new JPanel();
		groupLayout = new GroupLayout(gameFrame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap(577, Short.MAX_VALUE)
						.addComponent(playAreaPanel, GroupLayout.PREFERRED_SIZE, 309, GroupLayout.PREFERRED_SIZE)
						.addGap(468)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap(348, Short.MAX_VALUE)
						.addComponent(playAreaPanel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
						.addGap(282)));

		playerNameLabel = new JLabel("Enter your name");
		playerNameLabel.setHorizontalAlignment(SwingConstants.LEFT);

		playerNameLabel.setLocation(xCoordinate - 500, yCoordinate - 5);
		playAreaPanel.add(playerNameLabel);

		playerNameTextField = new JTextField();
		playerNameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		playerNameTextField.setColumns(10);
		playerNameTextField.setLocation(xCoordinate + (int) (playerNameLabel.getSize().getWidth()), yCoordinate - 5);
		playAreaPanel.add(playerNameTextField);

		connectButton = new JButton("PLAY>>");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 2. -TODO- Collect playerName
				String name = playerNameTextField.getText();
				if (name.length() != 0) {
					try {
						// 3. -TODO- login
						mController.login(name);
						// 5. Run the game. Look for more functions in Client
						// controller
						startGame();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		});
		playAreaPanel.add(connectButton);
		gameFrame.getContentPane().setLayout(groupLayout);
		gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gameFrame.setVisible(true);

		// 4. Subscribe for Fly position & score updates
		mController.setFlyPositionUpdateListener(this);
		mController.setPlayerPointsUpdateListener(this);

		// Note : View doesn't have access to server stubs and doesn't maintain
		// any
		// game state info except those required for UI display

	}

	protected void startGame() {
		playerNameLabel.setVisible(false);
		playerNameTextField.setVisible(false);
		connectButton.setVisible(false);
		gameFrame.getContentPane().remove(playAreaPanel);
		playAreaPanel = new JPanel();
		playAreaPanel.setBackground(Color.WHITE);
		playerListPanel = new JPanel();
		playerListPanel.setBackground(Color.WHITE);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(playAreaPanel, GroupLayout.PREFERRED_SIZE, 1108, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(playerListPanel, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup().addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(playerListPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 683,
										Short.MAX_VALUE)
						.addComponent(playAreaPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE))
				.addContainerGap()));
		playersListTextArea = new JTextArea();
		playersListTextArea.setEditable(false);
		playersListTextArea.setLineWrap(true);
		playersListTextArea.setRows(37);
		playersListTextArea.setWrapStyleWord(true);
		playersListTextArea.setSize(200, 600);
		playerListPanel.add(playersListTextArea);

		flyImage = new JLabel("");
		flyImage.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
		flyImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					mController.huntFly();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		flyImage.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("Fly.jpg")));
		playAreaPanel.add(flyImage);
		gameFrame.getContentPane().add(playAreaPanel);
		gameFrame.getContentPane().setLayout(groupLayout);
		gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gameFrame.setVisible(true);
		gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				gameFrame.setVisible(false);
				gameFrame.dispose();
			}
		});

		gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		playAreaPanel.setLayout(null);
	}

	@Override
	public void onPlayerPointsUpdate(String[] playerNames, int[] scores) {
		String gameScore = "";
		for (int i = 0; i < playerNames.length; i++) {
			gameScore += playerNames[i] + "\t: " + scores[i] + "\n";
		}
		playersListTextArea.setText(gameScore);
	}

	@Override
	public void onFlyPositionUpdate(int posX, int posY) {
		flyImage.setVisible(false);
		flyImage.setLocation(posX, posY);
		flyImage.setVisible(true);
		gameFrame.revalidate();
	}

}

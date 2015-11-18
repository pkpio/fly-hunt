package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import common.Constant;

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
	private JPanel loginPanel;
	private JPanel playerListPanel;
	private GroupLayout groupLayout;
	private JLabel playerNameLabel;
	private JButton connectButton;
	private JTextField playerNameTextField;
	private JPanel headerPanel;
	private JPanel scorePanel;
	private JPanel scoreHeader;
	Dimension screenSize;

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
		gameFrame = new JFrame("Fly Hunt");
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		gameFrame.setBounds(0, 0, 1024, 768);
		int xCoordinate, yCoordinate;
		xCoordinate = (int) gameFrame.getSize().getWidth() / 2;
		yCoordinate = (int) gameFrame.getSize().getHeight() / 2;
		loginPanel = new JPanel();
		groupLayout = new GroupLayout(gameFrame.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap(577, Short.MAX_VALUE)
						.addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, 309, GroupLayout.PREFERRED_SIZE)
						.addGap(468)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				groupLayout.createSequentialGroup().addContainerGap(348, Short.MAX_VALUE)
						.addComponent(loginPanel, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
						.addGap(282)));

		playerNameLabel = new JLabel("Enter your name");
		playerNameLabel.setHorizontalAlignment(SwingConstants.LEFT);

		playerNameLabel.setLocation(xCoordinate - 500, yCoordinate - 5);
		loginPanel.add(playerNameLabel);

		playerNameTextField = new JTextField();
		playerNameTextField.setHorizontalAlignment(SwingConstants.LEFT);
		playerNameTextField.setColumns(10);
		playerNameTextField.setLocation(xCoordinate + (int) (playerNameLabel.getSize().getWidth()), yCoordinate - 5);
		loginPanel.add(playerNameTextField);

		connectButton = new JButton("PLAY>>");
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Collect playerName
				String name = playerNameTextField.getText();
				if (name.length() != 0)
					try {
						mController.login(name);
						startGame();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
			}
		});

		gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		loginPanel.add(connectButton);
		gameFrame.getContentPane().setLayout(groupLayout);
		gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gameFrame.setVisible(true);

	}

	protected void startGame() {
		playerNameLabel.setVisible(false);
		playerNameTextField.setVisible(false);
		connectButton.setVisible(false);
		gameFrame.getContentPane().remove(loginPanel);
		playAreaPanel = new JPanel();
		playAreaPanel.setBackground(Color.WHITE);
		playerListPanel = new JPanel();
		setComplexPanel();// new JPanel();

		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.CENTER).addGroup(Alignment.LEADING,
				groupLayout.createSequentialGroup().addContainerGap()
						.addComponent(playAreaPanel, GroupLayout.PREFERRED_SIZE, Constant.CANVAS_WIDTH,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(playerListPanel, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.CENTER).addGroup(groupLayout
				.createSequentialGroup().addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.CENTER)
						.addComponent(playerListPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
								Constant.CANVAS_HEIGHT, Short.MAX_VALUE)
						.addComponent(playAreaPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
								Constant.CANVAS_HEIGHT, Short.MAX_VALUE))
				.addContainerGap()));

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
					e1.printStackTrace();
				}
			}
		});
		flyImage.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage("res/fly.jpg")));
		playAreaPanel.add(flyImage);
		gameFrame.getContentPane().add(playAreaPanel);
		gameFrame.getContentPane().setLayout(groupLayout);
	//	gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gameFrame.setVisible(true);
		gameFrame.addWindowListener(new WindowAdapter() {
			// WINDOW_CLOSING event handler
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				// You can still stop closing if you want to
				int res = JOptionPane.showConfirmDialog(gameFrame, "Are you sure you want to close?", "Close?",
						JOptionPane.YES_NO_OPTION);
				if (res == 0) {
					try {
						mController.logout();
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
					gameFrame.setVisible(false);
					gameFrame.dispose();
					System.exit(0);
				}
			}

			// WINDOW_CLOSED event handler
			@Override
			public void windowClosed(WindowEvent e) {
				super.windowClosed(e);
			}
		});
		
		gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		playAreaPanel.setLayout(null);

		// Subscribe for Fly position & score updates
		mController.setFlyPositionUpdateListener(this);
		mController.setPlayerPointsUpdateListener(this);

		// Notify controller that all set and ready to go
		mController.startGame();
	}

	private void setComplexPanel() {
		headerPanel = new javax.swing.JPanel();
		welcomeText = new javax.swing.JLabel();
		scoreHeader = new javax.swing.JPanel();
		playerNameHeaderLabel = new javax.swing.JLabel();
		playerScoreHeaderLabel = new javax.swing.JLabel();
		scorePanel = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		playersListTextArea = new javax.swing.JTextArea();
		welcomeText.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		welcomeText.setText("Welcome " + mController.mPlayerName);

		playerNameHeaderLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		playerNameHeaderLabel.setText("Player name");

		playerScoreHeaderLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
		playerScoreHeaderLabel.setText("score");

		javax.swing.GroupLayout scoreHeaderLayout = new javax.swing.GroupLayout(scoreHeader);
		scoreHeader.setLayout(scoreHeaderLayout);
		scoreHeaderLayout
				.setHorizontalGroup(scoreHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(scoreHeaderLayout.createSequentialGroup().addContainerGap()
								.addComponent(playerNameHeaderLabel).addGap(91, 91, 91)
								.addComponent(playerScoreHeaderLabel).addContainerGap(187, Short.MAX_VALUE)));
		scoreHeaderLayout.setVerticalGroup(
				scoreHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						scoreHeaderLayout.createSequentialGroup().addGap(0, 31, Short.MAX_VALUE)
								.addGroup(scoreHeaderLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
										.addComponent(playerNameHeaderLabel).addComponent(playerScoreHeaderLabel))));

		javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
		headerPanel.setLayout(headerPanelLayout);
		headerPanelLayout
				.setHorizontalGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(headerPanelLayout.createSequentialGroup().addGap(70, 70, 70).addComponent(welcomeText)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						headerPanelLayout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(
								scoreHeader, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		headerPanelLayout
				.setVerticalGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(headerPanelLayout.createSequentialGroup().addGap(31, 31, 31).addComponent(welcomeText)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(scoreHeader, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));

		javax.swing.GroupLayout scorePanelLayout = new javax.swing.GroupLayout(scorePanel);
		scorePanel.setLayout(scorePanelLayout);
		scorePanelLayout.setHorizontalGroup(scorePanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		scorePanelLayout.setVerticalGroup(scorePanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 182, Short.MAX_VALUE));

		playersListTextArea.setEditable(false);
		playersListTextArea.setLineWrap(true);
		playersListTextArea.setRows(37);
		playersListTextArea.setWrapStyleWord(true);
		jScrollPane1.setViewportView(playersListTextArea);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(playerListPanel);
		playerListPanel.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(headerPanel,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
								.addComponent(jScrollPane1))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(scorePanel,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addComponent(headerPanel, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(scorePanel, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jScrollPane1))));
		gameFrame.pack();
		gameFrame.setResizable(false);
	}

	@Override
	public void onPlayerPointsUpdate(String[] playerNames, int[] scores) {
		String gameScore = "";
		for (int i = 0; i < playerNames.length; i++) {
			gameScore += playerNames[i] + "\t\t " + scores[i] + "\n";
		}
		playersListTextArea.setText(gameScore);
	}

	@Override
	public void onFlyPositionUpdate(int posX, int posY) {
		flyImage.setVisible(false);
		System.out.println("Setting location"+posX);
		flyImage.setLocation(posX, posY);
		flyImage.setVisible(true);
		gameFrame.revalidate();
	}

	private javax.swing.JLabel playerNameHeaderLabel;
	private javax.swing.JLabel playerScoreHeaderLabel;
	private javax.swing.JLabel welcomeText;
	private javax.swing.JScrollPane jScrollPane1;
}

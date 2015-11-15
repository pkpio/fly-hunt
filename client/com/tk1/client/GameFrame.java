package com.tk1.client;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GameFrame {

	private ArrayList<ClientViewListener> actionListners = new ArrayList<ClientViewListener>();
	private JFrame gameFrame;
	private JTextArea playersListTextArea;
	private JLabel flyImage;
	private JPanel playAreaPanel;
	private JPanel playerListPanel;
	private GroupLayout groupLayout;
	private JLabel playerNameLabel;
	private JButton connectButton;
	private JTextField playerNameTextField;

	void positionFly(int x, int y) {

		flyImage.setVisible(false);
		flyImage.setLocation(x, y);
		flyImage.setVisible(true);
		gameFrame.revalidate();

	}

	void init() {
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
				int x = randInt(0, 400);
				int y = randInt(0, 300);
				positionFly(x, y);
				mouseClickEvent();
			}
		});
		flyImage.setIcon(new ImageIcon(GameFrame.class.getResource("Fly.jpg")));
		playAreaPanel.add(flyImage);
		gameFrame.getContentPane().add(playAreaPanel);
		gameFrame.getContentPane().setLayout(groupLayout);
		gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gameFrame.setVisible(true);
		gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				closeWindowEvent();

				gameFrame.setVisible(false);
				gameFrame.dispose();
			}
		});

		gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		playAreaPanel.setLayout(null);
	}

	int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	void setLoginUI() {

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

				String name = playerNameTextField.getText();
				if (name.length() != 0) {
					playButtonActionListener(name);
				}

			}
		});
		playAreaPanel.add(connectButton);

		gameFrame.getContentPane().setLayout(groupLayout);
		gameFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gameFrame.setVisible(true);

	}

	void playButtonActionListener(String name) {

		for (ClientViewListener cl : actionListners)
			cl.playerLogin(name);
	}

	void mouseClickEvent() {

		for (ClientViewListener cl : actionListners)
			cl.flieHunted();
	}

	void closeWindowEvent() {

		for (ClientViewListener listener : actionListners)
			listener.playerLogoff();
	}

	void updatePlayerList(String playerList) {

		playersListTextArea.setText(playerList);
	}

	void addListener(ClientViewListener listener) {

		actionListners.add(listener);
	}

}

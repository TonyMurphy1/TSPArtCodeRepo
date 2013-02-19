package view;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.TSPArtController;

public class ErrorFrame extends JFrame{
	
	public ErrorFrame(TSPArtController controller, String errorMessage) {
		this.setTitle("Error!");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		JLabel messageLabel = new JLabel(errorMessage);
		JPanel messagePanel = new JPanel();
		messagePanel.add(messageLabel);
		mainPanel.add(messagePanel);
		JButton okButton = new JButton("OK");
		okButton.setActionCommand("Error ok");
		okButton.addActionListener(controller);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(okButton);
		mainPanel.add(buttonPanel);
		add(mainPanel);
		setSize(400, 100);
		setResizable(false);
		setVisible(true);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
}

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

public class TSPArtInputFrame extends JFrame {
	private JComboBox TSPSolvingAlgorithm;
	private JButton okButton;
	private JButton cancelButton;
	
	public TSPArtInputFrame(ActionListener controller) {
		setTitle("TSP Solving Options");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		JPanel componentPanel = new JPanel();
		componentPanel.setLayout(new GridLayout(0,2));
		componentPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		mainPanel.add(componentPanel, BorderLayout.CENTER);
		add(mainPanel);
		
		JLabel tspAlgorithmLabel = new JLabel("TSP Solving Algorithm:");
		tspAlgorithmLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		tspAlgorithmLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		componentPanel.add(tspAlgorithmLabel);
		String[] TSPSolvingAlgorithms = {"Nearest Neighbour"};
		TSPSolvingAlgorithm = new JComboBox(TSPSolvingAlgorithms);
		TSPSolvingAlgorithm.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		componentPanel.add(TSPSolvingAlgorithm);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		buttonPanel.add(Box.createHorizontalGlue());
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		
		okButton = new JButton("OK");
		okButton.setActionCommand("TSP solve ok");
		okButton.addActionListener(controller);
		buttonPanel.add(okButton);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(2, 2)));
		
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("TSP solve cancel");
		cancelButton.addActionListener(controller);
		buttonPanel.add(cancelButton);
		
		JTextPane userHelpText = new JTextPane();
		userHelpText.setEditable(false);
		userHelpText.setText("Please choose the algorithm that will be used to generate the TSP art version of this image. Consult the user guide for an in-depth description of what each algorithm does.");
		userHelpText.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		userHelpText.setBackground(mainPanel.getBackground());
		mainPanel.add(userHelpText, BorderLayout.PAGE_START);
		
		setVisible(true);
		
		setSize(300, 175);
		setAlwaysOnTop(true);
		this.setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	public int getIndexOfSelectedAlgorithm() {
		return TSPSolvingAlgorithm.getSelectedIndex();
	}
}

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
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import controller.TSPArtController;

public class StippledImageInputFrame extends JFrame {
	
	private JSpinner horizontalCells;
	private JSpinner verticalCells;
	private JSpinner maxNoOfCitiesPerCell;
	private JComboBox stipplingAlgorithm;
	private JButton okButton;
	private JButton cancelButton;
	
	public StippledImageInputFrame(int maxHorizontalCells, int maxVerticalCells, int maxCitiesPerCell, TSPArtController controller) {
		setTitle("Image Stippling Options");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		JPanel componentPanel = new JPanel();
		componentPanel.setLayout(new GridLayout(0,2));
		componentPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		JLabel horizontalCellsLabel = new JLabel("No. of horizontal cells:");
		horizontalCellsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		horizontalCellsLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		componentPanel.add(horizontalCellsLabel);
		horizontalCells = new JSpinner(new SpinnerNumberModel(maxVerticalCells, 1, maxHorizontalCells, 1));
		horizontalCells.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		horizontalCells.addChangeListener(controller);
		componentPanel.add(horizontalCells);
		
		JLabel verticalCellsLabel = new JLabel("No. of vertical cells:");
		verticalCellsLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		verticalCellsLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		componentPanel.add(verticalCellsLabel);
		verticalCells = new JSpinner(new SpinnerNumberModel(maxVerticalCells, 1, maxVerticalCells, 1));
		verticalCells.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		verticalCells.addChangeListener(controller);
		componentPanel.add(verticalCells);
		
		JLabel maxCitiesLabel = new JLabel("Max cities per cell:");
		maxCitiesLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		maxCitiesLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		componentPanel.add(maxCitiesLabel);
		maxNoOfCitiesPerCell = new JSpinner(new SpinnerNumberModel(maxCitiesPerCell, 1, maxCitiesPerCell, 1));
		maxNoOfCitiesPerCell.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		componentPanel.add(maxNoOfCitiesPerCell);
		
		JLabel stipplingAlgorithmLabel = new JLabel("Stippling Algorithm:");
		stipplingAlgorithmLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		stipplingAlgorithmLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		componentPanel.add(stipplingAlgorithmLabel);
		String[] stipplingAlgorithms = {"Random"};
		stipplingAlgorithm = new JComboBox(stipplingAlgorithms);
		stipplingAlgorithm.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		componentPanel.add(stipplingAlgorithm);
		componentPanel.setSize(new Dimension(200, 25));
		mainPanel.add(componentPanel, BorderLayout.CENTER);
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		buttonPanel.add(Box.createHorizontalGlue());
		
		okButton = new JButton("OK");
		okButton.setActionCommand("Stipple ok");
		okButton.addActionListener(controller);
		buttonPanel.add(okButton);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(2, 2)));
		
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Stipple cancel");
		cancelButton.addActionListener(controller);
		buttonPanel.add(cancelButton);

		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		
		JTextPane userHelpText = new JTextPane();
		userHelpText.setEditable(false);
		userHelpText.setText("Please choose the number of horizontal and vertical cells in the grid used to stipple the image, as well as the maximum number of points which can appear in each cell. Finally, please choose the algorithm which will be used to stipple the image. Consult the user guide for an in-depth description of what each algorithm does.");
		userHelpText.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		userHelpText.setBackground(mainPanel.getBackground());
		mainPanel.add(userHelpText, BorderLayout.PAGE_START);
		
		add(mainPanel);
		
		setVisible(true);
		
		this.setSize(300, 275);
		this.setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
	
	public int[] getGridSize() {
		int[] gridSize = {Integer.parseInt(horizontalCells.getValue().toString()), Integer.parseInt(verticalCells.getValue().toString())};
		return gridSize;
	}
	
	public int getMaxCities() {
		return Integer.parseInt(maxNoOfCitiesPerCell.getValue().toString());
	}
	
	public int getIndexOfSelectedAlgorithm() {
		return stipplingAlgorithm.getSelectedIndex();
	}
	
	public int[] getCurrentGridSize() {
		int[] size = {Integer.parseInt(horizontalCells.getValue().toString()), Integer.parseInt(verticalCells.getValue().toString())};
		return size;
	}
	
	public void setMaxCitiesLimit(int maxCitiesPerCell) {
		if (maxCitiesPerCell > 0) maxNoOfCitiesPerCell.setModel(new SpinnerNumberModel(maxCitiesPerCell, 1, maxCitiesPerCell, 1));
	}
}

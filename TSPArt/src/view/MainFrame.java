package view;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame {

	private ImageIcon displayedImage;
	
	public MainFrame() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
		add(mainPanel);
		mainPanel.add(createButtonPanel());
		JPanel imagePanel= new JPanel();
		imagePanel.add(new JLabel(displayedImage));
		mainPanel.add(imagePanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();
		Dimension buttonSize = new Dimension(200, 25);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.add(createFixedSizeButton("Load Image", buttonSize, "Load an image"));
		buttonPanel.add(createFixedSizeButton("View Original Image", buttonSize, ""));
		buttonPanel.add(createFixedSizeButton("Greyscale Conversion", buttonSize, ""));
		buttonPanel.add(createFixedSizeButton("View Greyscale Image", buttonSize, ""));
		buttonPanel.add(createFixedSizeButton("Stipple Image", buttonSize, ""));
		buttonPanel.add(createFixedSizeButton("View Stippled Image", buttonSize, ""));
		buttonPanel.add(createFixedSizeButton("Solve As TSP Instance", buttonSize, ""));
		buttonPanel.add(createFixedSizeButton("View TSP Art Image", buttonSize, ""));
		buttonPanel.add(Box.createVerticalGlue());
		return buttonPanel;
	}
	
	private JButton createFixedSizeButton(String label, Dimension size, String tooltip) {
		JButton fixedSizeButton = new JButton(label);
		fixedSizeButton.setToolTipText(tooltip);
		fixedSizeButton.setPreferredSize(size);
		fixedSizeButton.setMinimumSize(size);
		fixedSizeButton.setMaximumSize(size);
		return fixedSizeButton;
	}
	
	public void setImage(Image image) {
		displayedImage.setImage(image);
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}

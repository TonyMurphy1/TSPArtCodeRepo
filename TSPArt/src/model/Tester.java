package model;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tester {

	public static void main(String[] args) {
		JFrame originalFrame = new JFrame("Original Image");
		originalFrame.setSize(1000, 1000);
		JPanel panel1 = new JPanel();
		originalFrame.add(panel1);
		TSPImage image = new TSPImage();
		image.loadImage("Bender.jpeg");
		JLabel imageLabel1 = new JLabel(new ImageIcon(image.getOriginalImage()));
		panel1.add(imageLabel1);
		originalFrame.setVisible(true);
		
		JFrame greyFrame = new JFrame("Greyscale Image");
		greyFrame.setSize(1000, 1000);
		JPanel panel2 = new JPanel();
		greyFrame.add(panel2);
		image.convertOriginalImageToGreyscale();
		JLabel imageLabel2 = new JLabel(new ImageIcon(image.getGreyScaleImage()));
		panel2.add(imageLabel2);
		greyFrame.setVisible(true);
		
		image.setGrid(50, 50);
		image.setMaxCitiesInCell(20);
		image.calculateAllCellsAverageGreyscaleValues();
		image.calculateNumberOfCitiesInAllCells();
		image.pickCityDistributor(0);
		image.distributeCities();
		
		JFrame cityViewFrame = new JFrame("Greyscale Image");
		cityViewFrame.setSize(1000, 1000);
		JPanel panel3 = new JPanel();
		cityViewFrame.add(panel3);
		JLabel imageLabel3 = new JLabel(new ImageIcon(image.getCityMapImage()));
		panel3.add(imageLabel3);
		cityViewFrame.setVisible(true);
		
		image.pickTSPSolver(0);
		image.solveInstanceOfTSP();
		
		JFrame pathViewFrame = new JFrame("TSP Path Image");
		pathViewFrame.setSize(1000, 1000);
		JPanel panel4 = new JPanel();
		pathViewFrame.add(panel4);
		JLabel imageLabel4 = new JLabel(new ImageIcon(image.getTSPImage()));
		panel4.add(imageLabel4);
		pathViewFrame.setVisible(true);
	}
}


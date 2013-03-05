package model;

import static org.junit.Assert.*;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.junit.Test;

public class Tester {
	
	TSPImage image;
	
	public Tester() {
//		image = createATSPImageToBeTested("Bender.jpeg", 200, 200, 3, 0, 0);
//		JFrame frame = new JFrame("Test");
//		frame.add(new JLabel(new ImageIcon(image.getTSPImage())));
//		frame.setSize(new Dimension(100,100));
//		frame.setVisible(true);
	}
	
	private TSPImage createATSPImageToBeTested(String pathOfImage, int horizontalCells, int verticalCells, int maxCitiesPerCell, int stipplingAlgorithm, int tspAlgorithm) {
		image = new TSPImage();
		image.loadImage(pathOfImage);
		image.convertOriginalImageToGreyscale();
		image.setGrid(horizontalCells, verticalCells);
		image.setMaxCitiesInCell(maxCitiesPerCell);
		image.calculateAllCellsAverageGreyscaleValues();
		image.calculateNumberOfCitiesInAllCells();
		image.pickCityDistributor(stipplingAlgorithm);
		image.distributeCities();
		image.pickTSPSolver(tspAlgorithm);
		image.solveInstanceOfTSP();
		return image;
	}
	
	@Test
	public void ensurePathIsATSPPath() {
		image = createATSPImageToBeTested("Bender.jpeg", 100, 100, 2, 0, 2);
		int[][] tspPath = image.getTSPPath();
		assertTrue(tspPathEndsWhereItStarts(tspPath) && tspPathIsTheSameLengthAsNoOfCities(image.getCities(), tspPath) && pathOnlyVisitsEachCityOnce(tspPath));
	}
	
	public boolean tspPathEndsWhereItStarts(int[][] tspPath) {
		if (tspPath.length > 0) return (tspPath[tspPath.length - 1][2] == tspPath[0][0]) && (tspPath[tspPath.length - 1][3] == tspPath[0][1]);
		else return true;
	}
	
	public boolean tspPathIsTheSameLengthAsNoOfCities(City[] cities, int[][] tspPath) {
		return (cities.length == tspPath.length);
	}
	
	public boolean pathOnlyVisitsEachCityOnce(int[][] tspPath) {
		int[][] locationsOfCitiesVisitedFromAnother = populate2DArrayWithMinusOne(new int[tspPath.length][2]);
		int[][] locationsOfCitiesVisitingAnother = populate2DArrayWithMinusOne(new int[tspPath.length][2]);
		int noOfEdgesChecked = 0;
		for (int i = 0; i < tspPath.length; i++) {
			int[] firstLocation = {tspPath[i][0], tspPath[i][1]};
			int[] secondLocation = {tspPath[i][2], tspPath[i][3]};
			if (isLocationInThisArrayOfLocations(firstLocation, locationsOfCitiesVisitingAnother) || isLocationInThisArrayOfLocations(secondLocation, locationsOfCitiesVisitedFromAnother)) return false;
			else {
				locationsOfCitiesVisitingAnother[noOfEdgesChecked] = firstLocation;
				locationsOfCitiesVisitedFromAnother[noOfEdgesChecked] = secondLocation;
				noOfEdgesChecked++;
			}
		}
		return true;
	}
	
	private boolean isLocationInThisArrayOfLocations(int[] location, int[][] locations) {
		for (int i = 0; i < locations.length; i++) {
			boolean isThisOneTheSameSoFar = true;
			for (int j = 0; j < locations[i].length; j++) {
				if(locations[i][j] != location[j]) isThisOneTheSameSoFar = false;
				if ((j == locations[i].length - 1) && isThisOneTheSameSoFar) return true;
			}
		}
		return false;
	}
	
	private int[][] populate2DArrayWithMinusOne(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array[i][j] = -1;
			}
		}
		return array;
	}
	
	public static void main(String[] args) {
		Tester t = new Tester();
	}
}

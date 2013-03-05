package model;


import java.util.Random;

public class RandomCityDistributor implements CityDistributor{

	private int[] grid;
	private int[][] noOfCitiesInCells;
	private int[] picSize;
	
	public RandomCityDistributor(int[] grid, int[][] noOfCitiesInCells, int picWidth, int picHeight) {
		this.grid = grid;
		this.noOfCitiesInCells = noOfCitiesInCells;
		picSize = new int[2];
		picSize[0] = picWidth;
		picSize[1] = picHeight;
	}
	
	//TODO may still be bugged, testing necessary
	@Override
	public City[] distributeCities() {
		Random placementPicker = new Random();
		City[] cityList = new City[calculateNumberOfCitiesOnGrid()];
		double horizontalCellSize = ((double) picSize[0])/grid[0];
		double verticalCellSize = ((double) picSize[1])/grid[1];
		int numberOfCitiesAdded = 0;
		for (int horizontalCellIndex = 0; horizontalCellIndex < grid[0]; horizontalCellIndex++) {
			for (int verticalCellIndex = 0; verticalCellIndex < grid[1]; verticalCellIndex++) {
				for (int noOfCitiesIndex = 0; noOfCitiesIndex < noOfCitiesInCells[horizontalCellIndex][verticalCellIndex]; noOfCitiesIndex++) {
					boolean cityPlaced = false;
					while (!cityPlaced) {
						int xPos = (int) (horizontalCellSize * horizontalCellIndex) + placementPicker.nextInt((int) horizontalCellSize + 1);
						int yPos = (int) (verticalCellSize * verticalCellIndex) + placementPicker.nextInt((int) verticalCellSize + 1);
						City randomlyPlacedCity = new City(xPos, yPos);
						if (!isThereACityAtThisPosition(randomlyPlacedCity.getXPos(), randomlyPlacedCity.getYPos(), cityList)) {
							cityList[numberOfCitiesAdded] = randomlyPlacedCity;
							cityPlaced = true;
						}
					}
					
					
					numberOfCitiesAdded++;
				}
			}
		}
		return cityList;
	}
	
	private int calculateNumberOfCitiesOnGrid() {
		int noOfCities = 0;
		int otherNoOfCities = 0;
		for (int horizontalCellIndex = 0; horizontalCellIndex < grid[0]; horizontalCellIndex++) {
			for (int verticalCellIndex = 0; verticalCellIndex < grid[1]; verticalCellIndex++) {
				
				for (int noOfCitiesIndex = 0; noOfCitiesIndex < noOfCitiesInCells[horizontalCellIndex][verticalCellIndex]; noOfCitiesIndex++) {
					noOfCities++;
				}
				
				int x = noOfCitiesInCells[horizontalCellIndex][verticalCellIndex];
				otherNoOfCities = otherNoOfCities + x;
			}
		}
		return noOfCities;
	}
	
	private boolean isThereACityAtThisPosition(int xPos, int yPos, City[] cities) {
		for (int cityIndex = 0; cityIndex < cities.length && cities[cityIndex] != null; cityIndex++) {
			if (cities[cityIndex].getXPos() == xPos && cities[cityIndex].getYPos() == yPos) {
				return true;
			}
		}
		return false;
	}
}

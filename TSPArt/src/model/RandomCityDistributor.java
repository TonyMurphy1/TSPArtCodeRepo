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
	
	//TODO bugged
	@Override
	public City[] distributeCities() {
		Random placementPicker = new Random();
		City[] cityList = new City[calculateNumberOfCitiesOnGrid()];
		int numberOfCitiesAdded = 0;
		for (int horizontalCellIndex = 0; horizontalCellIndex < grid[0]; horizontalCellIndex++) {
			for (int verticalCellIndex = 0; verticalCellIndex < grid[1]; verticalCellIndex++) {
				for (int noOfCitiesIndex = 0; noOfCitiesIndex < noOfCitiesInCells[horizontalCellIndex][verticalCellIndex]; noOfCitiesIndex++) {
					boolean cityPlaced = false;
					while (!cityPlaced) {
						City randomlyPlacedCity = new City((int) ((double)(picSize[0] / grid[0])) * horizontalCellIndex + placementPicker.nextInt((picSize[0] / grid[0])), (int) ((double)(picSize[1] / grid[1])) * verticalCellIndex+ placementPicker.nextInt((picSize[1] / grid[1])));
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

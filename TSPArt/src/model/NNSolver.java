package model;

import java.util.Random;

public class NNSolver implements TSPSolver {
	
	@Override
	public int[][] solve(City[] cities) {
		int[][] path = new int[cities.length][4];
		if (path.length > 0) {
			Random cityPicker = new Random();
			City firstCity = cities[cityPicker.nextInt(cities.length)];
			City firstCityOnEdge = firstCity;
			for (int cityIndex = 0; cityIndex < cities.length - 1; cityIndex++) {
				firstCityOnEdge.setVisited(true);
				City secondCityOnEdge = findNearestCityTo(firstCityOnEdge, cities);
				path[cityIndex][0] = firstCityOnEdge.getXPos();
				path[cityIndex][1] = firstCityOnEdge.getYPos();
				path[cityIndex][2] = secondCityOnEdge.getXPos();
				path[cityIndex][3] = secondCityOnEdge.getYPos();
				firstCityOnEdge = secondCityOnEdge;
			}
			path[cities.length-1][0] = firstCityOnEdge.getXPos();
			path[cities.length-1][1] = firstCityOnEdge.getYPos();
			path[cities.length-1][2] = firstCity.getXPos();
			path[cities.length-1][3] = firstCity.getYPos();
		}
		setAllCitiesToNotVisited(cities);
		return path;
	}
	
	private City findNearestCityTo(City city, City[] cities) {
		City currentNearest = null;
		for (int cityIndex = 0; cityIndex < cities.length; cityIndex++) {
			if (!city.equals(cities[cityIndex]) && !cities[cityIndex].getVisited()) {
				if (currentNearest == null) {
					currentNearest = cities[cityIndex];
				}
				else {
					if (city.distanceTo(cities[cityIndex].getXPos(), cities[cityIndex].getYPos()) < city.distanceTo(currentNearest.getXPos(), currentNearest.getYPos())) {
						currentNearest = cities[cityIndex];
					}
				}
			}
		}
		return currentNearest;
	}
	
	private void setAllCitiesToNotVisited(City[] cities) {
		for (int i = 0; i < cities.length; i++) {
			cities[i].setVisited(false);
		}
	}
}

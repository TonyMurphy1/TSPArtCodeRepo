package model;

import java.util.ArrayList;
import java.util.Random;

public class AntColonySolver implements TSPSolver {

	private double constantUsedForPheromoneCalculation = 1000;
	ArrayList<int[]> allEdgesTravelledSoFar = new ArrayList<int[]>();
	ArrayList<Double> pheromoneValuesOfEdges = new ArrayList<Double>();
	
	@Override
	public int[][] solve(City[] cities) {
		performInitialSearchAndAssignPheromoneValuesToEdges(cities);
		return performIterativeImprovement(cities);
	}

	private void performInitialSearchAndAssignPheromoneValuesToEdges(City[] cities) {
		NNSolver initialSolver = new NNSolver();
		int[][] path = initialSolver.solve(cities);
		changeListOfEdgesAndPheromoneValuesBasedOnThisPath(path);
	}
	
	private void changeListOfEdgesAndPheromoneValuesBasedOnThisPath(int[][] path) {
		double amountOfPheromone = calculateAmountOfPheromoneToBeDumped(calculateLengthOfPath(path));
		for(int i = 0; i < path.length; i++) addAnEdgeToTheListOfEdgesTravelled(path[i], amountOfPheromone);
	}
	
	private int[][] performIterativeImprovement(City[] cities) {
		int[][] path = new int[cities.length][];
		for (int noOfIterations = 0; noOfIterations < 100; noOfIterations++) {
			path = createNewPath(cities);
			changeListOfEdgesAndPheromoneValuesBasedOnThisPath(path);
			System.out.println(calculateLengthOfPath(path));
		}
		return path;
	}
	
	private int[][] createNewPath(City[] cities) {
		int[][] path = new int[cities.length][4];
		Random pathStartChooser = new Random();
		int indexOfPathStart = pathStartChooser.nextInt(cities.length);
		City[] citiesOnPath = new City[cities.length + 1];
		citiesOnPath[0] = cities[indexOfPathStart];
		citiesOnPath[citiesOnPath.length - 1] = cities[indexOfPathStart];
		citiesOnPath[0].setVisited(true);
		for (int i = 1; i < cities.length; i++) {
			citiesOnPath[i] = chooseNextCityToVisit(citiesOnPath[i-1], cities);
			citiesOnPath[i].setVisited(true);
		}
		for (int i = 0; i < path.length; i++) {
			path[i][0] = citiesOnPath[i].getXPos();
			path[i][1] = citiesOnPath[i].getYPos();
			path[i][2] = citiesOnPath[i + 1].getXPos();
			path[i][3] = citiesOnPath[i + 1].getYPos();
		}
		setAllCitiesToNotVisited(cities);
		return path;
	}
	
	private void setAllCitiesToNotVisited(City[] cities) {
		for (int i = 0; i < cities.length; i++) {
			cities[i].setVisited(false);
		}
	}
	
	private City chooseNextCityToVisit(City location, City[] cities) {
		City mostLikelyCandidateSoFar = null;
		double desirabilityOfCurrentMostLikelyCandidate = 0;
		for (int i = 0; i < cities.length; i++) {
			if (!cities[i].getVisited()) {
				if (mostLikelyCandidateSoFar == null) mostLikelyCandidateSoFar = cities[i];
				double newDesirablity = calculateDesirabilityOfCityGivenCurrentLocation(location, cities[i]);
				if (newDesirablity > desirabilityOfCurrentMostLikelyCandidate) {
					mostLikelyCandidateSoFar = cities[i];
					desirabilityOfCurrentMostLikelyCandidate = newDesirablity;
				}
			}
		}
		return mostLikelyCandidateSoFar;
	}
	
	public boolean isCityInThisListOfCities(City city, City[] cities) {
		for (int i = 0; i < cities.length; i++) {
			if (cities[i] != null && city.equals(cities[i])) {
				return true;
			}
		}
		return false;
	}
	
	private double calculateDesirabilityOfCityGivenCurrentLocation(City location, City destination) {
		if (destination == null) return 0;
		double distanceToCity = calculateDistanceBetweenTwoPoints(location.getXPos(), location.getYPos(), destination.getXPos(), destination.getYPos());
		int[] possibleEdge = {location.getXPos(), location.getYPos(), destination.getXPos(), destination.getYPos()};
		double amountOfPheromoneOnEdge = 0;
		int indexOfPossibleEdge = getIndexOfEdgeInListOfEdgesTravelled(possibleEdge);
		if (indexOfPossibleEdge != -1) {
			amountOfPheromoneOnEdge = pheromoneValuesOfEdges.get(indexOfPossibleEdge);
		}
		
		//TODO combine amount of pheromone and distance somehow
		return (constantUsedForPheromoneCalculation/distanceToCity) + amountOfPheromoneOnEdge;
	}
	
	private double calculateDistanceBetweenTwoPoints(int pointOneX, int pointOneY, int pointTwoX, int pointTwoY) {
		double xDistance = pointTwoX - pointOneX;
		double yDistance = pointTwoY - pointOneY;
		return Math.sqrt((xDistance*xDistance) + (yDistance*yDistance));
	}
	
	//TODO change constant to affect the amount of pheromone dumped on an edge
	private double calculateAmountOfPheromoneToBeDumped(double pathLength) {
		return (constantUsedForPheromoneCalculation/pathLength);
	}
	
	private int addAnEdgeToTheListOfEdgesTravelled(int[] edge, double pheromoneValue) {
		int edgesIndex = getIndexOfEdgeInListOfEdgesTravelled(edge);
		if (edgesIndex == -1) {
			allEdgesTravelledSoFar.add(edge);
			pheromoneValuesOfEdges.add(pheromoneValue);
			return 0;
		} else {
			pheromoneValuesOfEdges.set(edgesIndex, pheromoneValuesOfEdges.get(edgesIndex) + pheromoneValue);
			return 1;
		}
	}
	
	private int getIndexOfEdgeInListOfEdgesTravelled(int[] edge) {
		int[] flippedEdge = {edge[3], edge[2], edge[1], edge[0]};
		for (int i = 0; i < allEdgesTravelledSoFar.size(); i++) {
			if (areTwoIntArraysValuesTheSame(allEdgesTravelledSoFar.get(i),edge)) return i;
			if (areTwoIntArraysValuesTheSame(allEdgesTravelledSoFar.get(i),flippedEdge)) return i;
		}
		return -1;
	}
	
	private boolean areTwoIntArraysValuesTheSame(int[] edgeOne, int[] edgeTwo) {
		if (edgeOne != null && edgeTwo != null && edgeOne.length == edgeTwo.length) {
			for (int i = 0; i < edgeOne.length; i++) {
				if (edgeOne[i] != edgeTwo[i]) return false;
			}
		}
		return true;
	}
	
	private double calculateLengthOfPath(int[][] path) {
		double pathLength = 0;
		for (int edgeIndex = 0; edgeIndex < path.length; edgeIndex++) {
			double xDistance = path[edgeIndex][2] - path[edgeIndex][0];
			double yDistance = path[edgeIndex][3] - path[edgeIndex][1];
			double distance = Math.sqrt((xDistance*xDistance) + (yDistance*yDistance));
			pathLength += distance;
		}
		return pathLength;
	}
	
	public static void main(String[] args) {
		AntColonySolver a = new AntColonySolver();
		int[] x = {1,2,3,4};
		int[] y = {4,2,3,1};
		a.addAnEdgeToTheListOfEdgesTravelled(x, 0);
		City[] cities = new City[5];
		City z = new City(100, 100);
		City b = new City(100, 100);
		//cities[0] = z;
		System.out.println(a.isCityInThisListOfCities(z, cities));
		
	}
}

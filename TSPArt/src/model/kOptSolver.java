package model;

import java.util.Random;

public class kOptSolver implements TSPSolver{

	private int k;
	private int minimumIterations;
	
	public kOptSolver(int k, int minimumIterations) {
		this.k = k;
		this.minimumIterations = minimumIterations;
	}
	
	@Override
	public int[][] solve(City[] cities) {
		int[][] path = new int[cities.length][4];
		if (path.length > 0) {
			NNSolver greedySolver = new NNSolver();
			path = greedySolver.solve(cities);
			path = improveSolution(path);
		}
		return path;
	}
	
	private int[][] improveSolution(int[][] path) {
		int iterationsSinceLastChange = 0;
		while (iterationsSinceLastChange < minimumIterations) {
			int[][][] disjointPaths = separatePath(path);
			int[][] possiblyImprovedSolution = rejoinPathsUsingBruteForce(disjointPaths, path.length);
			if (calculateLengthOfPath(possiblyImprovedSolution) < calculateLengthOfPath(path)) {
				path = possiblyImprovedSolution;
				iterationsSinceLastChange = 0;
				System.out.println(calculateLengthOfPath(path));
			}
			iterationsSinceLastChange++;
		}
		return path;
	}
	
	private int[][][] separatePath(int[][] path) {
		int[][][] disjointPaths = new int[k][][];
		int[] indexOfEdgesToBeRemoved = calculateIndexesOfEdgesToBeRemoved(path);
		int firstPathLength = indexOfEdgesToBeRemoved[0] + (path.length - indexOfEdgesToBeRemoved[indexOfEdgesToBeRemoved.length - 1] - 1);
		int[][] firstDisjointPath = new int[firstPathLength][4];
		for (int i = 0; i < (path.length - indexOfEdgesToBeRemoved[indexOfEdgesToBeRemoved.length - 1] - 1); i++) {
			firstDisjointPath[i] = path[i + indexOfEdgesToBeRemoved[indexOfEdgesToBeRemoved.length - 1] + 1];
		}
		for (int i = 0; i < indexOfEdgesToBeRemoved[0]; i++) {
			firstDisjointPath[i + (path.length - indexOfEdgesToBeRemoved[indexOfEdgesToBeRemoved.length - 1] - 1)] = path[i];
		}
		disjointPaths[0] = firstDisjointPath;
		for (int index = 1; index < indexOfEdgesToBeRemoved.length; index++) {
			int previousIndex = indexOfEdgesToBeRemoved[index-1];
			int pathLength = indexOfEdgesToBeRemoved[index] - previousIndex - 1;
			int[][] disjointPath = new int[pathLength][4];
			int disjointPathIndex = 0;
			for (int edgeIndex = indexOfEdgesToBeRemoved[index] - pathLength; edgeIndex < indexOfEdgesToBeRemoved[index]; edgeIndex++) {
				disjointPath[disjointPathIndex] = path[edgeIndex];
				disjointPathIndex++;
			}
			disjointPaths[index] = disjointPath;
		}
		
		
		return disjointPaths;
	}
	
	//TODO could be more efficient?
	private int[][] rejoinPathsUsingBruteForce(int[][][] paths, int pathLength) {
		int[][] path = new int[pathLength][4];
		int[][][] possiblePaths = getAllPossibleCombinations(paths);
		path = calculateShortestPathCombo(possiblePaths);
		return path;
	}
	
	private int calculateNumberOfPossibleCombinations() {
		int combinations = k;
		for (int i = k - 1; i > 1; i--) {
			combinations *= i;
		}
		return combinations;
	}
	
	private int[][][] getAllPossibleCombinations(int[][][] paths) {
		int possibleNumberOfCombinations = calculateNumberOfPossibleCombinations();
		int[][][][] allPossibleCombinations = new int[possibleNumberOfCombinations][][][];
		allPossibleCombinations[0] = paths;
		int noOfFoundPaths = 1;
		while (noOfFoundPaths < possibleNumberOfCombinations) {
			for (int indexOfFirstPathToBeSwapped = 0; indexOfFirstPathToBeSwapped < k; indexOfFirstPathToBeSwapped++) {
				for (int indexOfSecondPathToBeSwapped = 0; indexOfSecondPathToBeSwapped < k; indexOfSecondPathToBeSwapped++) {
					if (indexOfFirstPathToBeSwapped != indexOfSecondPathToBeSwapped) {
						for (int currentlyFoundPathsIndex = 0; currentlyFoundPathsIndex < noOfFoundPaths; currentlyFoundPathsIndex++) {
							int[][] firstPath = allPossibleCombinations[currentlyFoundPathsIndex][indexOfFirstPathToBeSwapped].clone();
							int[][] secondPath = allPossibleCombinations[currentlyFoundPathsIndex][indexOfSecondPathToBeSwapped].clone();
							int[][][] newPathCombo = allPossibleCombinations[currentlyFoundPathsIndex].clone();
							newPathCombo[indexOfSecondPathToBeSwapped] = firstPath;
							newPathCombo[indexOfFirstPathToBeSwapped] = secondPath;
							if (!hasPathCombinationAlreadyBeenFound(allPossibleCombinations, newPathCombo, noOfFoundPaths)) {
								allPossibleCombinations[noOfFoundPaths] = newPathCombo;
								noOfFoundPaths++;
							}
						}
					}
				}
			}
		}
		int[][][] possiblePathsJoined = new int[allPossibleCombinations.length][][];
		for (int possibleCombosIndex = 0; possibleCombosIndex < allPossibleCombinations.length; possibleCombosIndex++) {
			possiblePathsJoined[possibleCombosIndex] = connectPaths(allPossibleCombinations[possibleCombosIndex]);
		}
		return possiblePathsJoined;
	}
	
	private int[][] connectPaths(int[][][] paths) {
		int pathLength = k;
		for (int pathsIndex = 0; pathsIndex < paths.length; pathsIndex++) {
			pathLength += paths[pathsIndex].length;
		}
		int[][] connectedPath = new int[pathLength][];
		int noOfPathsAdded = 0;
		for (int pathsIndex = 0; pathsIndex < paths.length; pathsIndex++) {
			for (int pathIndex = 0; pathIndex < paths[pathsIndex].length; pathIndex++) {
				connectedPath[noOfPathsAdded] = paths[pathsIndex][pathIndex];
				noOfPathsAdded++;
			}
			if (noOfPathsAdded > 0) {
				int[] firstEdge = connectedPath[noOfPathsAdded-1];
				int[] secondEdge = null;
				if (pathsIndex < paths.length - 1) secondEdge = paths[pathsIndex + 1][0];
				else secondEdge = connectedPath[0];
				int[] connector = {firstEdge[2], firstEdge[3], secondEdge[0], secondEdge[1]};
				connectedPath[noOfPathsAdded] = connector;
				noOfPathsAdded++;
			}
		}
		return connectedPath;
	}
	
	private int[][] calculateShortestPathCombo(int[][][] possibleCombos) {
		int[][] currentShortestPath;
		if(possibleCombos.length > 0) currentShortestPath = possibleCombos[0];
		else return null;
		for (int possibleCombosIndex = 0; possibleCombosIndex < possibleCombos.length; possibleCombosIndex++) {
			double possibleShortestPathLength = calculateLengthOfPath(possibleCombos[possibleCombosIndex]);
			if (possibleShortestPathLength < calculateLengthOfPath(currentShortestPath)) {
				currentShortestPath = possibleCombos[possibleCombosIndex];
			}
		}
		return currentShortestPath;
	}
	
	private boolean hasPathCombinationAlreadyBeenFound(int[][][][] possibleCombinations, int[][][] newPathCombo, int noOfCombinations) {
		for (int possibleComboIndex = 0; possibleComboIndex < noOfCombinations; possibleComboIndex++) {
			if (areTwo3DArraysTheSame(possibleCombinations[possibleComboIndex], newPathCombo)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean areTwo3DArraysTheSame(int[][][] pathComboOne, int[][][] pathComboTwo) {
		if (pathComboOne.length == pathComboTwo.length) {
			for (int i = 0; i < pathComboOne.length; i++) {
				if (pathComboOne[i].length == pathComboTwo[i].length) {
					for (int j = 0; j < pathComboOne[i].length; j++) {
						if (pathComboOne[i][j].length == pathComboTwo[i][j].length) {
							for (int k = 0; k < pathComboOne[i][j].length; k++) {
								if (pathComboOne[i][j][k] != pathComboTwo[i][j][k]) return false;
							}
						} else return false;
					}
				} else return false;
			}
		} else return false;
		return true;
	}
	
	private int[] calculateIndexesOfEdgesToBeRemoved(int[][] path) {
		Random edgeChooser = new Random();
		int[] edgeIndexes = new int[k];
		boolean edgesNotSuitable = true;
		while(edgesNotSuitable) {
			for (int index = 0; index < edgeIndexes.length; index++) {
				edgeIndexes[index] = edgeChooser.nextInt(path.length);
			}
			edgeIndexes = sortEdgeIndexes(edgeIndexes);
			edgesNotSuitable = !verifyEdgeIndexesAreSuitable(edgeIndexes, path);
		}
		return edgeIndexes;
	}
	
	private boolean verifyEdgeIndexesAreSuitable(int[] edgeIndexes, int[][] path) {
		if (edgeIndexes[0] == 0 && edgeIndexes[k - 1] == path.length - 1) return false;
		for (int firstIndex = 0; firstIndex < edgeIndexes.length-1; firstIndex++) {
			for (int secondIndex = firstIndex + 1; secondIndex < edgeIndexes.length; secondIndex++) {
				if (edgeIndexes[firstIndex] == edgeIndexes[secondIndex] || edgeIndexes[firstIndex] == edgeIndexes[secondIndex]+1 || edgeIndexes[firstIndex] == edgeIndexes[secondIndex]-1) {
					return false;
				}
			}
		}
		return true;
	}
	
	private int[] sortEdgeIndexes(int[] edgeIndexes) {
		if (edgeIndexes.length == 1) return edgeIndexes;
		boolean sorted = false;
		int sortingIndex = 1;
		int noOfMovements = 0;
		while (!sorted) {
			if (edgeIndexes[sortingIndex] < edgeIndexes[sortingIndex - 1]) {
				int firstIndex = edgeIndexes[sortingIndex - 1];
				int secondIndex = edgeIndexes[sortingIndex];
				edgeIndexes[sortingIndex] = firstIndex;
				edgeIndexes[sortingIndex - 1] = secondIndex;
				noOfMovements++;
			}
			sortingIndex++;
			if (sortingIndex == edgeIndexes.length) {
				if (noOfMovements == 0) sorted = true;
				else {
					sortingIndex = 1;
					noOfMovements = 0;
				}
			}
		}
		return edgeIndexes;
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
}

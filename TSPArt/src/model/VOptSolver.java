package model;

import java.util.Random;

public class VOptSolver {
	private int pickFirstEdgeToBeReplacedIndex(int[][] path) {
		Random edgeChooser = new Random();
		int firstEdge = edgeChooser.nextInt(path.length);
		return firstEdge;
	}
	
	private int getIndexOfSecondEdge(int[][] path, int firstEdgeIndex) {
		int[][] triedEdges = new int[path.length][4];
		triedEdges[0] = path[firstEdgeIndex];
		int noOfTriedEdges = 1;
		while (noOfTriedEdges < triedEdges.length/4) {
			Random edgeChooser = new Random();
			int nextEdgeIndex = edgeChooser.nextInt(path.length);
			if (!doesEdgeExistInArrayOfEdges(triedEdges, path[nextEdgeIndex])) {
				int[] newEdge = {path[firstEdgeIndex][2], path[firstEdgeIndex][3], path[nextEdgeIndex][0], path[nextEdgeIndex][1]};
				triedEdges[noOfTriedEdges] = newEdge;
				noOfTriedEdges++;
				if (calculateLengthOfAnEdge(newEdge) < calculateLengthOfAnEdge(path[firstEdgeIndex]) && !doesEdgeExistInArrayOfEdges(path, newEdge)) return nextEdgeIndex;
			}
		}
		return -1;
	}
	
	private boolean doesEdgeExistInArrayOfEdges(int[][] path, int[] edge) {
		for (int i = 0; i < path.length; i++) {
			int[] flippedEdge = {path[i][3], path[i][2], path[i][1], path[i][0]};
			boolean theSameSoFar = true;
			boolean flippedTheSameSoFar = true;
			for (int j = 0; j < 4; j++) {
				if (path[i][j] != edge[j]) theSameSoFar = false;
				if (path[i][j] != flippedEdge[j]) theSameSoFar = false;
				if (j == 3 && (theSameSoFar || flippedTheSameSoFar)) return true;
			}
		}
		return false;
	}
	
	private double calculateLengthOfAnEdge(int[] edge) {
		double xDistance = edge[2] - edge[0];
		double yDistance = edge[3] - edge[1];
		return Math.sqrt((xDistance*xDistance) + (yDistance*yDistance));
	}
}

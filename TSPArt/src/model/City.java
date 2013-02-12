package model;

public class City {
	private int xPos;
	private int yPos;
	private boolean visited;
	
	public City(int xPosition, int yPosition) {
		xPos = xPosition;
		yPos = yPosition;
		visited = false;
	}
	
	public int getXPos() {
		return xPos;
	}
	
	public int getYPos() {
		return yPos;
	}
	
	public void moveCity(int newXPosition, int newYPosition) {
		xPos = newXPosition;
		yPos = newYPosition;
	}
	
	public double distanceTo(int xPosition, int yPosition) {
		double xDistance = xPosition - xPos;
		double yDistance = yPosition - yPos;
		double distance = Math.sqrt((xDistance*xDistance) + (yDistance*yDistance));
		return distance;
	}
	
	public boolean getVisited() {
		return visited;
	}
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}


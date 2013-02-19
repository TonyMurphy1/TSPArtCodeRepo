package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import model.TSPImage;
import view.ErrorFrame;
import view.MainFrame;
import view.StippledImageInputFrame;
import view.TSPArtInputFrame;

public class TSPArtController implements ActionListener, ComponentListener {

	private MainFrame mainFrame;
	private TSPImage tspimage;
	private StippledImageInputFrame stippledImageOptionsFrame;
	private TSPArtInputFrame tspImageoptionsFrame;
	private ErrorFrame errorFrame;
	private short currentlyDisplayedImageMode;
	private int[] maxGridCells = {200, 200};
	private int maxCitiesPerCell = 10;
	
	public TSPArtController (MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		tspimage = new TSPImage();
		currentlyDisplayedImageMode = -1;
	}
	
	@Override
	public void actionPerformed(ActionEvent aE) {
		switch(aE.getActionCommand()) {
			case "Load Image":
				File image = mainFrame.showLoadFileWindow();
				if (image != null) {
					if (tspimage.loadImage(image.getPath()) == 0) {
						currentlyDisplayedImageMode = 0;
						mainFrame.loadImageClicked();
					} else {
						errorFrame = new ErrorFrame(this, "Error: File not compatible, file not loaded!");
						mainFrame.setEnabled(false);
					}	
				}
				break;
			case "View Original Image":
				currentlyDisplayedImageMode = 0;
				break;
			case "Greyscale Conversion":
				if (tspimage.convertOriginalImageToGreyscale() == 0) {
					currentlyDisplayedImageMode = 1;
					mainFrame.greyscaleConversionClicked();
				}
				break;
			case "View Greyscale Image":
				currentlyDisplayedImageMode = 1;
				break;
			case "Stipple Image":
				stippledImageOptionsFrame = new StippledImageInputFrame(maxGridCells[0], maxGridCells[1], maxCitiesPerCell, this);
				mainFrame.setEnabled(false);
				break;
			case "View Stippled Image":
				currentlyDisplayedImageMode = 2;
				break;
			case "Solve As TSP Instance":
				tspImageoptionsFrame = new TSPArtInputFrame(this);
				mainFrame.setEnabled(false);
				break;
			case "View TSP Art Image":
				currentlyDisplayedImageMode = 3;
				break;
			case "Stipple ok":
				tspimage.setGrid(stippledImageOptionsFrame.getGridSize()[0], (stippledImageOptionsFrame.getGridSize()[1]));
				tspimage.setMaxCitiesInCell(stippledImageOptionsFrame.getMaxCities());
				tspimage.calculateAllCellsAverageGreyscaleValues();
				tspimage.calculateNumberOfCitiesInAllCells();
				tspimage.pickCityDistributor(stippledImageOptionsFrame.getIndexOfSelectedAlgorithm());
				tspimage.distributeCities();
				currentlyDisplayedImageMode = 2;
				mainFrame.stippleImageClicked();
				stippledImageOptionsFrame.dispose();
				mainFrame.setVisible(true);
				mainFrame.setEnabled(true);
				break;
			case "Stipple cancel":
				stippledImageOptionsFrame.dispose();
				mainFrame.setVisible(true);
				mainFrame.setEnabled(true);
				break;
			case "TSP solve ok":
				tspimage.pickTSPSolver(tspImageoptionsFrame.getIndexOfSelectedAlgorithm());
				tspimage.solveInstanceOfTSP();
				currentlyDisplayedImageMode = 3;
				mainFrame.solveTSPInstanceClicked();
				tspImageoptionsFrame.dispose();
				mainFrame.setVisible(true);
				mainFrame.setEnabled(true);
				break;
			case "TSP solve cancel":
				tspImageoptionsFrame.dispose();
				mainFrame.setVisible(true);
				mainFrame.setEnabled(true);
				break;
			case "Error ok":
				errorFrame.dispose();
				mainFrame.setEnabled(true);
				mainFrame.setVisible(true);
				break;
		}
		setImage();		
	}
	
	public MainFrame getMainFrame() {
		return mainFrame;
	}
	
	private void setImage() {
		switch(currentlyDisplayedImageMode) {
			case -1:
				mainFrame.setImage(mainFrame.getNoImageLoadedImage().getScaledInstance(mainFrame.getImageWidth(), mainFrame.getImageHeight(), 0));
				break;
			case 0:
				mainFrame.setImage(tspimage.getOriginalImage().getScaledInstance(mainFrame.getImageWidth(), mainFrame.getImageHeight(), 0));
				break;
			case 1:
				mainFrame.setImage(tspimage.getGreyScaleImage().getScaledInstance(mainFrame.getImageWidth(), mainFrame.getImageHeight(), 0));
				break;
			case 2:
				mainFrame.setImage(tspimage.getCityMapImage().getScaledInstance(mainFrame.getImageWidth(), mainFrame.getImageHeight(), 0));
				break;
			case 3:
				mainFrame.setImage(tspimage.getTSPImage().getScaledInstance(mainFrame.getImageWidth(), mainFrame.getImageHeight(), 0));
				break;
		}
	}

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		TSPArtController controller = new TSPArtController(mainFrame);
		mainFrame.setListener(controller);
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {}

	@Override
	public void componentMoved(ComponentEvent arg0) {}

	@Override
	public void componentResized(ComponentEvent arg0) {
		setImage();
	}

	@Override
	public void componentShown(ComponentEvent arg0) {}
}

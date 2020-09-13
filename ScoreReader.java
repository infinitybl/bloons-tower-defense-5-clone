/*
	ScoreReader.java
 	Phillip Pham
 	Class used to update high scores, write high scores, and display high scores.
*/

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO; //used to render high scores text
import java.awt.Graphics2D; //same reason as above

public class ScoreReader {
	public int lineNumber; //total number of lines other than the first one in "High Scores.txt"
	public ArrayList<Score> scoreList; //holds Score objects
	public ScoreComparator comparator;	//used in Collections.sort() to sort the scoreList
	public int lowestRoundIndex; //index of the lowest score in the scoreList arrayList
	public int lowestRound; //used to compare scores to see which one is the lowest
	
	//CONSTRUCTOR
	public ScoreReader() {
		scoreList = new ArrayList<Score>();
		comparator = new ScoreComparator();
	}
	
	//getHighestRound() returns the highest score in the "High Score.txt" file
	public int getHighestRound() {
		readHighScores();
		Collections.sort(scoreList, comparator);
		return scoreList.get(0).highestRound;
	}

	//readHighScores() reads the "High Scores.txt" file and creates
	//Score objects using the data to add to the scoreList
	public void readHighScores() {
		try {
			//used to read data file "High Scores.txt"
    		BufferedReader reader = new BufferedReader(new FileReader("Data Files/High Scores.txt"));
    		int lineNumber = Integer.parseInt(reader.readLine().trim()); //gets how much lines are in text file
			scoreList = new ArrayList<Score>();
			
    		//read the score file line by line and create Score objects
    		for (int i = 0; i < lineNumber; i++) {
            	scoreList.add(new Score(reader.readLine().trim()));
    		}

    		reader.close();

		}
		catch (IOException ex) {
			System.out.println("Error reading scores from file");
		}
	}

	//getScoreImage() renders high score text on an image so that it can be displayed
	//Got help from https://stackoverflow.com/questions/10391778/create-a-bufferedimage-from-file-and-make-it-type-int-argbs
	public BufferedImage getScoreImage() {

		try {
			BufferedImage in = ImageIO.read(new File("UI/Scores.png"));
			BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);

			Graphics2D g = newImage.createGraphics();
			g.setColor(new Color(255, 255, 255));
			g.fillRect(0, 0, newImage.getWidth(), newImage.getHeight()); //makes the whole image white

	        readHighScores(); //get updated scoreList

	        g.setFont(new Font("Oetztype", Font.PLAIN, 30));
			g.setColor(new Color(255, 0, 0));

			//display all the Scores line by line by drawing on newImage
	        for (int l = 0; l < scoreList.size(); l++) {
	        	g.drawString("" + (l + 1) + ": " + scoreList.get(l).getDisplayString(), 75, 30 + 50 * l);
	        	System.out.println(scoreList.get(l).getDisplayString());
	        }
	        g.dispose();
	        
	        return newImage;

	       
		}
		catch (IOException ex) {
			System.out.println("Error reading image from file");
		}
		
		return null;

	}
	
	//updateScores() adds a new Score object whenever player reaches game over
	public void updateScores(Menu mainMenu, int currentRound) {
		String userName = mainMenu.textInput.getText(); //used to hold the name input from the textfield in the enter score menu page

		readHighScores(); //get high scores from the text file and adds them to the scoresList

	    scoreList.add(new Score("" + userName + "," + currentRound));
	    //add the user's new Score to the scoreList

		lowestRound = Integer.MAX_VALUE;

		//find the lowestRound in the scoreList and get its index
		for (int j = 0; j < scoreList.size(); j++) {
			if (scoreList.get(j).highestRound < lowestRound) {
				lowestRoundIndex = j;
				lowestRound = scoreList.get(j).highestRound;
			}

		}

		scoreList.remove(lowestRoundIndex); //remove the lowest Score

		Collections.sort(scoreList, comparator); //sort the scoreList from highest score to lowest

		try {
			BufferedWriter output = new BufferedWriter(new FileWriter("Data Files/High Scores.txt"));
    		output.flush(); //clear the text file
    		output.write("" + scoreList.size()); //write the line number on the top line
			for (int k = 0; k < scoreList.size(); k++) {
				output.newLine();
    			output.write(scoreList.get(k).getString()); //write each score on a new line
			}
			output.close();
		}
		catch (IOException ex) {
			System.out.println("Error writing scores to file");
		}
		


	}

}
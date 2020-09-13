/*  Menu.java
 	Phillip Pham
	Class used to generate the main menu of the game.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

class Menu extends JFrame {

	//JBUTTONS
	public JButton playBtn = new JButton("Play");
	public JButton helpBtn = new JButton("Help");
	public JButton scoresBtn = new JButton("Scores");
	public JButton returnBtn1 = new JButton("Return");
	public JButton returnBtn2 = new JButton("Return");
	public JButton confirmBtn = new JButton("Confirm Name");

	//LOGO AND HELP JLABEL
	public JLabel helpLabel;
	public JLabel mainMenuLabel;

	//ALL MENU PAGES (JLAYEREDPANES)
	public JLayeredPane mPage = new JLayeredPane(); //LayeredPane allows to control what shows on top
	public JLayeredPane hPage = new JLayeredPane();
	public JLayeredPane sPage = new JLayeredPane();
	public JLayeredPane ePage = new JLayeredPane();

	//JTEXTFIELD to enter name for high scores
	JTextField textInput = new JTextField("Enter Name", 20);

	//CONSTRUCTOR
	public Menu() {

		//SETTING SIZE AND LOCATION OF ALL BUTTONS
		playBtn.setSize(100, 30);
		playBtn.setLocation(430, 500);

		helpBtn.setSize(100, 30);
		helpBtn.setLocation(430, 540);

		scoresBtn.setSize(100, 30);
		scoresBtn.setLocation(430, 580);

		returnBtn1.setSize(100, 30);
		returnBtn1.setLocation(25, 630);

		returnBtn2.setSize(100, 30);
		returnBtn2.setLocation(25, 630);

		confirmBtn.setSize(150, 30);
		confirmBtn.setLocation(780, 630);

		textInput.setSize(200, 30);
		textInput.setLocation(200, 100);
		

		//PAGES
		//******************** mPage ************************
		//central menu page

		mPage.setLayout(null);

		//The numbers I use when adding to the LayeredPane are just relative to one another. Higher numbers on top.
		ImageIcon mainMenuImage = new ImageIcon("UI/Opening_Screen.png");
		mainMenuLabel = new JLabel(mainMenuImage);
		mainMenuLabel.setSize(960, 720);
		mainMenuLabel.setLocation(0, 0);
		mPage.add(mainMenuLabel, 1);
		
		
		//adding buttons to mPage
		mPage.add(playBtn, 2);
		mPage.add(helpBtn, 3);
		mPage.add(scoresBtn, 4);

		
		//******************** hPage ************************
		//help page
		hPage.setLayout(null);
		
		ImageIcon help = new ImageIcon("UI/Instructions.png");
		helpLabel = new JLabel(help);
		helpLabel.setSize(960, 600);
		helpLabel.setLocation(0, 0);

		//adds helpLabel (instructions image) to hPage
		hPage.add(helpLabel, 1);
		
		//adding buttons to hPage
		hPage.add(returnBtn1, 2);

		//******************** sPage ************************
		//high scores page
		sPage.setLayout(null);

		//adding buttons to sPage
		sPage.add(returnBtn2, 2);

		//******************** ePage ************************
		//enter score page
		ePage.setLayout(null);

		//adding buttons and text field to ePage
		ePage.add(textInput, 2);
		ePage.add(confirmBtn, 2);

	}

}
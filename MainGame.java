/*  MainGame.java
 	Phillip Pham
	Contains main method to create a JFrame that runs the BloonsTD game, plus the GamePanel class to control overall
	game logic and to render graphics on screen.
*/

import java.util.*;
import java.io.*; //used for file input and output
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import javax.swing.Timer; //used for Timer


//MainGame class that holds main method
public class MainGame extends JFrame implements ActionListener {
	public JPanel cards;   //a panel that uses CardLayout
	public CardLayout cLayout; //layout used for menu
	public ScoreReader sReader; //used for display high scores

	public Timer gameTimer; //controls game's framerate
	public GamePanel game; //generates GamePanel object to run the game

	public Menu mainMenu; //generates a Menu object to create a menu
	
	public Sound jazzTheme = new Sound("Sounds/Jazz.wav");


	//CONSTRUCTOR
	public MainGame() {
		super("Bloons Tower Defense 5 - By: Phillip Pham");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(960, 720);
		
		cLayout = new CardLayout();
		sReader = new ScoreReader();
		
		jazzTheme.loop();
		gameTimer = new Timer(1, this);

		//generates a new menu
		mainMenu = new Menu();
		
		//adds action listeners to all the buttons from the mainMenu
		mainMenu.playBtn.addActionListener(this);
		mainMenu.helpBtn.addActionListener(this);
		mainMenu.scoresBtn.addActionListener(this);
		mainMenu.returnBtn1.addActionListener(this);
		mainMenu.returnBtn2.addActionListener(this);
		mainMenu.confirmBtn.addActionListener(this);

		//create a new JPanel with a card layout
		cards = new JPanel(cLayout);
		//add each page (menu screens) from the mainMenu to the JPanel
		cards.add(mainMenu.mPage, "menu");
		cards.add(mainMenu.hPage, "help");
		cards.add(mainMenu.sPage, "scores");
		cards.add(mainMenu.ePage, "enter score");

		add(cards); //add the JPanel to the MainGame's JFrame

		setResizable(false);
		setVisible(true);

	}

	// ------------ ActionListener ------------------------------------------
	// Checks for button click and Timer events
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();
		
		//when the user selects the play button, create new GamePanel instance
		//and display the game page to start the game
		if (source == mainMenu.playBtn) {
			jazzTheme.stop();
			game = new GamePanel();
			cards.add(game, "game");
			cLayout.show(cards, "game");
			gameTimer.start(); //start the gameTimer
			game.requestFocus();
		}

		//display help page
		if (source == mainMenu.helpBtn) {
			cLayout.show(cards, "help");
			mainMenu.hPage.requestFocus();
		}

		//display high scores page
		if (source == mainMenu.scoresBtn) {
			updateSPage();
			cLayout.show(cards, "scores");
			mainMenu.sPage.requestFocus();

		}

		//returns to the central menu page whenever user clicks on a return button
		if (source == mainMenu.returnBtn1 || source == mainMenu.returnBtn2) {
			jazzTheme.loop();
			cLayout.show(cards, "menu");
			mainMenu.mPage.requestFocus();
		}
		
		//updates and displats high scores page after user enters name in the
		//enter scores page
		if (source == mainMenu.confirmBtn) {
			sReader.updateScores(mainMenu, game.round);
			updateSPage();
			cLayout.show(cards, "scores");
			mainMenu.sPage.requestFocus();
		}
		
		//whenever the gameTimer is ticked, check if the game is still running,
		//update state, and render all the graphics on screen using repaint
		if (source == gameTimer) {
			if (game != null && game.ready && game.running) {
				game.updateState();
				game.repaint();
			}
			if (game.running == false) {
				gameTimer.stop();
				//if player died, show enter score page
				if (game.lives <= 0) {
					cLayout.show(cards, "enter score");
					mainMenu.ePage.requestFocus();
				}
				//if player pressed home button, show menu page
				else {
					jazzTheme.loop();
					cLayout.show(cards, "menu");
				}
				
			}
		}

	}
	
	//updateSPage() updates the high scores page
	public void updateSPage() {
		mainMenu.sPage = new JLayeredPane();
		mainMenu.sPage.setLayout(null);

		//adding return button to sPage
		mainMenu.sPage.add(mainMenu.returnBtn2, 2);
		mainMenu.returnBtn2.addActionListener(this);
		//getting the image that shows the high scores
		JLabel scoreLabel = new JLabel(new ImageIcon(sReader.getScoreImage()));
	    scoreLabel.setSize(960, 600);
	    scoreLabel.setLocation(0, 0);
	    mainMenu.sPage.add(scoreLabel);

	    cards.add(mainMenu.sPage, "scores");
		
	}
	
	public static void main(String[] args) {
		MainGame frame = new MainGame(); //create new JFrame (window for the game)
    }



}


//GamePanel class controls the main game logic and updates the graphics on the screen
class GamePanel extends JPanel implements MouseMotionListener, MouseListener {
	//Track background
	private Image trackImage = new ImageIcon("Tracks/Ocean_Road.png").getImage();
	
	public boolean ready; //used to determine if the all the game's assets already loaded yet
	public int money; //player's current amount of money
	public int lives; //player's current amount of lives
	public int round; //current round player is on

	private Scanner inFile; //used to read data files
	
	//SELECTING/PLACING VARIABLES
	public String currentSelectedTowerButton; //used to determine which tower button is currently selected
	public boolean placingTower; //used to check if player is placing a tower
	public boolean selectingTower; //used to check if plater is selecting a tower
	public Tower towerToBePlaced; //current Tower that player is placing on screen
	public Tower currentSelectedTower; //currentTower that player is selecting

	public SideBar sb; 
	
	//BUTTONS
	public ArrayList<GameButton> towerButtons; 
	public GameButton fastForwardButton;
	public GameButton homeButton;
	public GameButton goButton;
	public GameButton sellButton;

	public boolean fastForward; //used to check if game is fast forwarded

	public int currentMouseX;
	public int currentMouseY;
	
	//counter and tick variables used to determine when to add more bloons 
	//(after 30 frames pass)
	public int bloonAddDelayCounter;
	public final int bloonAddDelayTick = 30;

	public boolean running; //check if the game is still running
	public boolean roundRunning; //check if the current round is still running
	
	//GAME OBJECTS
	public ArrayList<Tower> towersList;
	public ArrayList<Bloon> bloonsList;
	public LinkedList<Bloon> bloonsStoreList;
	public ArrayList<Projectile> projectileList;
	
	//SOUNDS
	private Sound mainTheme = new Sound("Sounds/MainTheme.wav");
	private Sound gameEnd = new Sound("Sounds/GameEnd.wav");
	private Sound nonSelect = new Sound("Sounds/NonSelect.wav");
	private Sound place = new Sound("Sounds/Place.wav");
	private Sound select = new Sound("Sounds/Select.wav");
	private Sound sell = new Sound("Sounds/Sell.wav");

	//CONSTRUCTOR
	public GamePanel() {

		mainTheme.loop();

		running = true;

		towersList = new ArrayList<Tower>();
		bloonsList = new ArrayList<Bloon>();
		bloonsStoreList = new LinkedList<Bloon>();
		projectileList = new ArrayList<Projectile>();

		trackImage = trackImage.getScaledInstance(775, 570, Image.SCALE_SMOOTH);
		
		money = 30000;
		lives = 150;
		round = 0;
		currentSelectedTowerButton = "";

		towerToBePlaced = null;

		roundRunning = false;
		placingTower = false;
		selectingTower = false;
		fastForward = false;
		bloonAddDelayCounter = 0;

		try {
			inFile = new Scanner(new File("Data Files/Round Data.txt")); //gets data file
		}
		catch (IOException ex) {
			System.out.println("Couldn't find Round Data.txt");
		}

		inFile.nextLine(); //skip first line at beginning of file
		addBloons(); //add first round Bloons

		sb = new SideBar(money, lives, round, currentSelectedTower, currentSelectedTowerButton);
		
		//add buttons
		towerButtons = new ArrayList<GameButton>();
		towerButtons.add(new GameButton("dartMonkey", 800, 90));
		towerButtons.add(new GameButton("tackShooter", 880, 90));
		towerButtons.add(new GameButton("sniperMonkey", 800, 145));
		towerButtons.add(new GameButton("boomerangThrower", 880, 145));
		towerButtons.add(new GameButton("ninjaMonkey", 800, 200));
		towerButtons.add(new GameButton("bombTower", 880, 200));
		towerButtons.add(new GameButton("iceTower", 800, 260));
		towerButtons.add(new GameButton("glueGunner", 880, 260));
		towerButtons.add(new GameButton("superMonkey", 800, 315));
		towerButtons.add(new GameButton("monkeyApprentice", 880, 315));
		towerButtons.add(new GameButton("bananaFarm", 800, 375));
		towerButtons.add(new GameButton("spikeFactory", 880, 375));
		towerButtons.add(new GameButton("roadSpikes", 800, 435));
		towerButtons.add(new GameButton("explodingPineapple", 880, 435));

		fastForwardButton = new GameButton("fastForward", 815, 490);
		homeButton = new GameButton("home", 910, 650);
		goButton = new GameButton("go", 815, 575);
		sellButton = new GameButton("sell", 680, 575);

		currentMouseX = 0;
		currentMouseY = 0;
		
		addMouseMotionListener(this);
		addMouseListener(this);
		addNotify(); //calls addNotify() to indicate game is done loading

	}

	//addNotify() indicates game is done loading
	public void addNotify() {
        super.addNotify();
        ready = true;
    }
	
	//clearTrack() removes all Projectile, road spikes, and pineapple instances from screen
	public void clearTrack() {
		projectileList.clear();
		for (int j = towersList.size() - 1; j >= 0; j--) {
			towersList.get(j).attackDelayCounter = 0;
			if (towersList.get(j).towerType.equals("roadSpikes") || towersList.get(j).towerType.equals("explodingPineapple")) {
				towersList.remove(j);
			}
		}
	}
	
	//updateState() updates the game everytime the GameTimer from the MainGame class ticks
	public void updateState() {
		
		//when the game is not fast forwarded, call Thread.sleep() to slow game down
		if (fastForward == false) {
			try {
            	Thread.sleep(15);
            }
            catch (Exception ex) {
            	ex.printStackTrace();
            }

		}
		
		//update Side Bar information
		sb.updateGameInfo(money, lives, round, currentSelectedTower, currentSelectedTowerButton);
		
		//when there are no more Bloons left to add and the current round is finished, reset the delay counter,
		//add Bloons for the next round, set roundRunning to false, and clear the track
		if (bloonsList.size() == 0 && bloonsStoreList.size() == 0) {
			bloonAddDelayCounter = 0;
			addBloons();
			roundRunning = false;
			clearTrack();
			System.gc(); //avoid memory leak
		}
		
		//if the current round is going on
		if (roundRunning == true) {
			//add Bloons from the store list whenever the delay counter ticks
			if (bloonsStoreList.size() > 0 && bloonAddDelayCounter == bloonAddDelayTick) {
				bloonAddDelayCounter = 0;
				bloonsList.add(bloonsStoreList.pop());
			}
			//otherwise, increase the counter by 1
			else if (bloonsStoreList.size() > 0) {
				bloonAddDelayCounter++;
			}
			
			//CHECKING ALL BLOON OBJECTS
			for (int i = bloonsList.size() - 1; i >= 0; i--) {
				bloonsList.get(i).checkStatusEffects();
				bloonsList.get(i).move();
				
				//checking if Bloon escaped from screen
				if (bloonsList.get(i).x < 0 || bloonsList.get(i).x > 775 || bloonsList.get(i).y < 0 || bloonsList.get(i).y > 570) {
					lives -= bloonsList.get(i).health; //reduce lives
					bloonsList.addAll(bloonsList.get(i).breakIntoBloons());
					bloonsList.remove(i);
				}
				
				//checking if Bloon is removed after being popped by a Tower
				if (i < bloonsList.size() && i >= 0 && bloonsList.get(i).isRemoved == true) {
					money += bloonsList.get(i).moneyValue; //increase player's money
					bloonsList.addAll(bloonsList.get(i).breakIntoBloons());
					bloonsList.remove(i);
				}
			}
			
			//CHECKING ALL PROJECTILE OBJECTS
			for (int k = projectileList.size() - 1; k >= 0; k--) {
				projectileList.get(k).move();
				projectileList.get(k).checkIfExploding();
				bloonsList = projectileList.get(k).checkForCollision(bloonsList);
				
				//remove Projectile if necessary
				if (projectileList.get(k).isRemoved == true) {
					projectileList.remove(k);
				}
			}
			
			//CHECKING ALL TOWER OBJECTS
			for (int j = towersList.size() - 1; j >= 0; j--) {
				//call different check methods depending on Tower function
				if (towersList.get(j).towerType.equals("iceTower") || towersList.get(j).towerType.equals("explodingPineapple") ||
					towersList.get(j).towerType.equals("roadSpikes")) {
					bloonsList = towersList.get(j).checkIfCanAffectBloon(bloonsList);
					towersList.get(j).checkIfExploding(); 
				}
				else if (towersList.get(j).towerType.equals("bananaFarm")) {
					money = towersList.get(j).checkIfCanAddMoney(money);
				}
				else if (towersList.get(j).towerType.equals("spikeFactory")) {
					towersList = towersList.get(j).checkIfCanAddRoadSpikes(towersList);
				}
				else {
					projectileList = towersList.get(j).checkIfCanAddProjectile(bloonsList, projectileList);
				}
				
				//remove Tower if necessary
				if (towersList.get(j).isRemoved == true) {
					towersList.remove(j);
				}
			}
		}
		
		//stop the game if player's health falls to 0
		if (lives <= 0) {
			stopGame();
		}
	}
	
	//addBloons() reads data from a data file to determine what Bloon objects should be
	//added to the next round 
	public void addBloons() {
		//all Bloons start at top right hand corner of screen
		int startingX = 680;
		int startingY = 1;
		bloonsList.clear();
		bloonsStoreList.clear();
		if (bloonsList.size() == 0 && bloonsStoreList.size() == 0) {
			round++; //increase round number
			if (inFile.hasNextLine()) {
				String dataLine = inFile.nextLine().substring(5);
				String[] dataList = dataLine.split("/");
				String[] numberBloonPair;
				//parses through next line in data file and add the 
				//respective Bloons indicated
				for (String line : dataList) {
					numberBloonPair = line.trim().split(" ");
					for (int i = 0; i < Integer.parseInt(numberBloonPair[0]); i++) {
						bloonsStoreList.add(new Bloon(numberBloonPair[1], startingX, startingY, 0));
					}
				}
			}
			//when there are no more rounds left, stop the game
			else {
				stopGame();
			}

		}
	}
	
	
	//placeTower() creates a new Tower instance for the player to place if they
	//are currently placing a Tower
	public void placeTower(int mx, int my) {
		if (placingTower == true) {
			if (!currentSelectedTowerButton.equals("")) {
				towerToBePlaced = new Tower(currentSelectedTowerButton, mx, my);
			}
		}
	}

	//paintComponent() is used to draw all the graphics on the screen
	@Override
    public void paintComponent(Graphics g) {
    	//use float to draw transparently
    	float alpha = 0.75f;
		int type = AlphaComposite.SRC_OVER;
		AlphaComposite composite = AlphaComposite.getInstance(type, alpha);
		Color colour;

    	g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, getWidth(), getHeight()); //make the whole screen black

		g.drawImage(trackImage, 0, 0, this); //draws track
	
		sb.paintSideBar(g); //draw side bar UI

		Graphics2D g2 = (Graphics2D)g;
        Stroke stroke = new BasicStroke(4f);  //used to draw borders
		g2.setStroke(stroke);
		
		//drawing all tower buttons
		for (GameButton btn : towerButtons) {
			g.drawImage(btn.getImage(), btn.x, btn.y, this);
			//draw red border if button is current selected
			if (currentSelectedTowerButton.equals(btn.label)) {
				g2.setColor(new Color(225, 0, 0));
				g2.drawRect(btn.x - 2, btn.y - 2, btn.width + 2, btn.height + 2);
			}
			//draw white border if mouse is over button
			else if (btn.getRect().contains(currentMouseX, currentMouseY)) {
				g2.setColor(new Color(225, 225, 225));
				g2.drawRect(btn.x - 2, btn.y - 2, btn.width + 2, btn.height + 2);
			}
		}

		g2.setColor(new Color(225, 225, 225));
		
		//draw white border if mouse is over button
		if (fastForwardButton.getRect().contains(currentMouseX, currentMouseY)) {
			g2.drawRect(fastForwardButton.x - 2, fastForwardButton.y - 2, fastForwardButton.width + 2, fastForwardButton.height + 2);
		}
		else if (homeButton.getRect().contains(currentMouseX, currentMouseY)) {
			g2.drawRect(homeButton.x - 2, homeButton.y - 2, homeButton.width + 2, homeButton.height + 2);
		}
		else if (roundRunning == false && goButton.getRect().contains(currentMouseX, currentMouseY)) {
			g2.drawRect(goButton.x - 2, goButton.y - 2, goButton.width + 2, goButton.height + 2);
		}
		else if (currentSelectedTower != null && sellButton.getRect().contains(currentMouseX, currentMouseY) && !currentSelectedTower.towerType.equals("roadSpikes") && !currentSelectedTower.towerType.equals("explodingPineapple")) {
			g2.drawRect(sellButton.x - 2, sellButton.y - 2, sellButton.width + 2, sellButton.height + 2);
			//NOTE: can't sell road spikes or pineapples
		}
		
		g.drawImage(fastForwardButton.getImage(), fastForwardButton.x, fastForwardButton.y, this);
		g.drawImage(homeButton.getImage(), homeButton.x, homeButton.y, this);
		
		if (currentSelectedTower != null && !currentSelectedTower.towerType.equals("roadSpikes") && !currentSelectedTower.towerType.equals("explodingPineapple")) {
			g.drawImage(sellButton.getImage(), sellButton.x, sellButton.y, this);
		}
		if (roundRunning == false) {
			g.drawImage(goButton.getImage(), goButton.x, goButton.y, this);
		}
		
		//painting all Bloon objects
		for (Bloon bloon : bloonsList) {
			bloon.paint(g2);
		}
		
		//painting all Tower objects
		for (Tower tower : towersList) {
			//painting range circle around currently selected Tower
			if (currentSelectedTower != null && currentSelectedTower.equals(tower)) {
				colour = new Color(0, 0, 0, alpha); //black
				g2.setPaint(colour);
				g2.fillOval(tower.getCenterX() - tower.rangeRadius - (tower.width / 2), tower.getCenterY() - tower.rangeRadius - (tower.height / 2), tower.rangeRadius * 2, tower.rangeRadius * 2);
			}
			tower.paint(g2);
		}
		
		//painting all Projectile objects
		for (Projectile projectile : projectileList) {
			projectile.paint(g2);
		}
		
		//when the player is placing a Tower
		if (towerToBePlaced != null) {

			//draw range circle
			if (money >= towerToBePlaced.costValue && towerToBePlaced.getRect().isInside(new Rect(0 - 50, 0 - 50, 775 + 50, 570 + 50))) {
				colour = new Color(0, 0, 0, alpha); //black if player can place Tower at the respective location
			} 
			else {
				colour = new Color(0.5f, 0, 0, alpha); //red if player can't afford Tower or place at the respective location
			}

  			g2.setPaint(colour);

			int centerX = towerToBePlaced.getCenterX();
			int centerY = towerToBePlaced.getCenterY();
			
			//paint Tower using the center coordinates from which it was placed
			g2.fillOval(centerX - towerToBePlaced.rangeRadius - (towerToBePlaced.width / 2), centerY - towerToBePlaced.rangeRadius - (towerToBePlaced.height / 2), towerToBePlaced.rangeRadius * 2, towerToBePlaced.rangeRadius * 2);
			g.drawImage(towerToBePlaced.getImage(), (int)(towerToBePlaced.x - (towerToBePlaced.width / 2)), (int)(towerToBePlaced.y - (towerToBePlaced.height / 2)), this);
		}

		sb.paintSideBarText(g);
    }
	
	//stopGame() sets running to false, which stops the game
	public void stopGame() {
		running = false;
		mainTheme.stop();
		gameEnd.play();
	}	

	// ---------- MouseMotionListener ------------------------------------------
	// Check for mouse motion events
    public void mouseDragged(MouseEvent e) {}
	
    public void mouseMoved(MouseEvent e) {
    	//update current mouse coordinates
    	currentMouseX = e.getX();
    	currentMouseY = e.getY();
		
		//create new Tower instance wherever player is placing tower
    	if (placingTower == true) {
    		placeTower(currentMouseX, currentMouseY);
    	}

    }

	

	// ------------ MouseListener ------------------------------------------
	// Check for mouse click events
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {
    	//when player left clicks
    	if (e.getButton() == MouseEvent.BUTTON1) {
    		//add Tower to screen if player placed it inside the track and can afford it 
    		if (selectingTower == false && placingTower == true && towerToBePlaced != null && money >= towerToBePlaced.costValue && towerToBePlaced.getRect().isInside(new Rect(0 - 50, 0 - 50, 775 + 50, 570 + 50))) {
    			money -= towerToBePlaced.costValue; //decrease money
    			towersList.add(towerToBePlaced);
    			place.play();
    		}
    		
    		//check if player clicked on a tower button and set placingTower to true if they did
    		for (GameButton btn : towerButtons) {
				if (btn.getRect().contains(currentMouseX, currentMouseY)) {
					selectingTower = false;
					currentSelectedTower = null;
					placingTower = true;
					currentSelectedTowerButton = btn.label;
					select.play();
				}
			}
			
			//check if player clicked on a owned Tower and set selectingTower to true if they did
			if (placingTower == false) {
				for (Tower tower : towersList) {
					if (tower.getRect().contains(currentMouseX, currentMouseY)) {
						selectingTower = true;
						currentSelectedTower = tower;
						select.play();
					}
				}
			}
			
			//stop game if player presses on the home button
			if (homeButton.getRect().contains(currentMouseX, currentMouseY)) {
				stopGame();
			}
			
			//toggle fast forward if player presses the fast forward button
			if (fastForwardButton.getRect().contains(currentMouseX, currentMouseY)) {
				if (fastForward == true) {
					fastForward = false;
					fastForwardButton.buttonImageNormal = fastForwardButton.fastForwardImage1;
				}
				else if (fastForward == false) {
					fastForward = true;
					fastForwardButton.buttonImageNormal = fastForwardButton.fastForwardImage2;
				}
			}
			
			//sell a Tower if player pressed the sell button while currently selecting the Tower
			if (sellButton.getRect().contains(currentMouseX, currentMouseY) && !currentSelectedTower.towerType.equals("roadSpikes") && !currentSelectedTower.towerType.equals("explodingPineapple")) {
				for (int j = towersList.size() - 1; j >= 0; j--) {
					if (towersList.get(j).equals(currentSelectedTower)) {
						money += towersList.get(j).sellValue; //increase money
						towersList.remove(j);
						sell.play();
					}
				}

				selectingTower = false;
    			currentSelectedTower = null;
			}
			
			//start the current round if player pressed the go button 
			if (roundRunning == false && goButton.getRect().contains(currentMouseX, currentMouseY)) {
				bloonAddDelayCounter = 0;
				roundRunning = true;
			}

    	}
		
		//when player right clicks
    	else if (e.getButton() == MouseEvent.BUTTON3) {
    		//deselect any current selections (placing/selecing Tower)
    		if (placingTower == true) {
    			towerToBePlaced = null;
    			placingTower = false;
    			currentSelectedTowerButton = "";
    		}
    		if (selectingTower == true) {
    			selectingTower = false;
    			currentSelectedTower = null;
    		}
    		nonSelect.play();
    	}
    }

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

}

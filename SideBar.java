/*
	SideBar.java
 	Phillip Pham
 	Class used to store information on what to draw to the bar UI in the game. 
 	Makes main class less cluttered.
*/

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class SideBar extends JPanel {
	//IMAGES
	private Image sideBarRightImage = new ImageIcon("UI/Side_Bar_Right.png").getImage().getScaledInstance(200, 600, Image.SCALE_SMOOTH);
	private Image sideBarBottomImage = new ImageIcon("UI/Side_Bar_Bottom.png").getImage().getScaledInstance(960, 150, Image.SCALE_SMOOTH);
	private Image moneyImage = new ImageIcon("UI/Money.png").getImage();
	private Image healthImage = new ImageIcon("UI/Health.png").getImage();
	
	//GAME INFO VARIABLES
	private int currentMoney;
	private int currentLives;
	private int currentRound;
	private Tower currentSelectedTower;
	private String currentSelectedTowerButton;

	//CONSTRUCTOR
	public SideBar(int currentMoney, int currentLives, int currentRound, Tower currentSelectedTower, String currentSelectedTowerButton) {
		this.currentMoney = currentMoney;
		this.currentLives = currentLives;
		this.currentRound = currentRound;
		this.currentSelectedTower = currentSelectedTower;
		this.currentSelectedTowerButton = currentSelectedTowerButton;
	}
	
	//updateGameInfo() updates the game information variables in the Side Bar class
	public void updateGameInfo(int currentMoney, int currentLives, int currentRound, Tower currentSelectedTower, String currentSelectedTowerButton) {
		this.currentMoney = currentMoney;
		this.currentLives = currentLives;
		this.currentRound = currentRound;
		this.currentSelectedTower = currentSelectedTower;
		this.currentSelectedTowerButton = currentSelectedTowerButton;
	}
	
	//paintSideBar() paints the images of the Side Bar on screen
    public void paintSideBar(Graphics g) {
		g.drawImage(sideBarRightImage, 775, 0, this);
		g.drawImage(sideBarBottomImage, 0, 560, this);

		g.drawImage(moneyImage, 780, 5, this);
		g.drawImage(healthImage, 780, 35, this);

	}
	
	//paintSideBarText() renders what text should be displayed on the Side Bar UI
	//depending on the game information variables
	public void paintSideBarText(Graphics g) {
		
		g.setFont(new Font("Oetztype", Font.PLAIN, 16));
		g.setColor(new Color(255, 255, 255));
		
		//renders basic current info about game
		g.drawString("Money: " + currentMoney, 810, 20);
		g.drawString("Lives: " + currentLives, 810, 50);
		g.drawString("Round: " + currentRound, 25, 670);
		
		//when player is not currently selecting a Tower, display info about the Tower they are 
		//currently placing (or don't display anything if they are not placing a Tower)
		if (currentSelectedTower == null) {
			String textToBeDrawn = getSideBarTextInfo();
			int lineCounter = 0;
			for (String line : textToBeDrawn.split("\n")) {
            	g.drawString(line, 25, 590 + 18 * lineCounter);
            	lineCounter++;
			}
		}
		//when player is currently selecing a Tower, display info about that Tower
		else {
			g.drawString("Name: " + currentSelectedTower.towerType.substring(0, 1).toUpperCase() + currentSelectedTower.towerType.substring(1), 150, 600);
			g.drawString("Attack Power (damage): " + currentSelectedTower.attackPower, 150, 620);	
			g.drawString("Attack Time (FPS): " + currentSelectedTower.attackDelayTick, 400, 620);
			if (currentSelectedTower.towerType.equals("sniperMonkey")) {
				g.drawString("Range Radius (pixels): OO", 150, 640);
			}
			else if (currentSelectedTower.towerType.equals("bananaFarm") || currentSelectedTower.towerType.equals("roadSpikes")) {
				g.drawString("Range Radius (pixels): Null", 150, 640);
			}
			else {
				g.drawString("Range Radius (pixels): " + currentSelectedTower.rangeRadius, 150, 640);
			}
			
			g.drawString("Projectile Speed (pixels): " + currentSelectedTower.projectileSpeed, 400, 640);
			
			//add sell button
			if (!currentSelectedTower.towerType.equals("roadSpikes") && !currentSelectedTower.towerType.equals("explodingPineapple")) {
				g.drawString("Sell for ", 690, 620);
				g.drawString("$" + currentSelectedTower.sellValue, 690, 640);
			}
			//display how much more spikes a road spike Tower has
			if (currentSelectedTower.towerType.equals("roadSpikes")) {
				g.drawString("Remaining Spikes: " + currentSelectedTower.health, 150, 660);
			}
		}
	}
	
	//getSideBarTextInfo() returns a String to display on the screen if player is currently placing a Tower
	public String getSideBarTextInfo() {
		if (!currentSelectedTowerButton.equals("")) {
			if (currentSelectedTowerButton.equals("dartMonkey")) {
				return "Dart Monkey: Shoot a single dart that pops a single bloon. A good, cheap tower \nsuitable for the early rounds. Cost: $200.";
			}
			else if (currentSelectedTowerButton.equals("tackShooter")) {
				return "Tack Shooter: Shoots 8 tacks spread in all directions. Each tack can pop 1 bloon. \nCost: $360.";
			}
			else if (currentSelectedTowerButton.equals("sniperMonkey")) {
				return "Sniper Monkey: Armed with a high-tech long range rifle, snipes \nbloons with unlimited range. Cost: $350.";
			}
			else if (currentSelectedTowerButton.equals("boomerangThrower")) {
				return "Boomerang Thrower: Throws a boomerang that can go back to the monkey. Each \nboomerang can pop 3 bloons. Cost: $380.";
			}
			else if (currentSelectedTowerButton.equals("ninjaMonkey")) {
				return "Ninja Monkey: Stealthy tower that can see Camo Bloons and throws sharp \nshurikens rapidly. Cost: $500.";
			}
			else if (currentSelectedTowerButton.equals("bombTower")) {
				return "Bomb Tower: Launches a bomb that explodes on impact. Can destroy many \nbloons at once. Cost: $650.";
			}
			else if (currentSelectedTowerButton.equals("iceTower")) {
				return "Ice Tower: Freezes nearby bloons with every pulse. Frozen bloons are \nimmune to anything sharp. Cost: $300.";
			}
			else if (currentSelectedTowerButton.equals("glueGunner")) {
				return "Glue Gunner: Shoot a glob of monkey glue at a single bloon. Glued \nbloons move more slowly than normal. Cost: $270.";
			}
			else if (currentSelectedTowerButton.equals("superMonkey")) {
				return "Super Monkey: Shoots lasers incredibly fast. Has long range and is \nthe best tower in the game. Cost: $3500.";
			}
			else if (currentSelectedTowerButton.equals("monkeyApprentice")) {
				return "Monkey Apprentice: 	Trained in the arts of monkey magic, the Monkey \nApprentice weaves magical bolts that pop bloons. Each shot can pop 2 bloons. \nCost: $550.";
			}
			else if (currentSelectedTowerButton.equals("bananaFarm")) {
				return "Banana Farm: Banana Farms work hard to bring in extra cash every round. \nThey do not attack, so place them away from strategic areas. \nCost: $1000.";
			}
			else if (currentSelectedTowerButton.equals("spikeFactory")) {
				return "Spike Factory: Generates piles of road spikes on bits of nearby track. \nEach pile can pop 5 bloons, and unused spikes disappear at end of each round. \nCost: $750.";
			}
			else if (currentSelectedTowerButton.equals("roadSpikes")) {
				return "Road Spikes: Place these on the track to pop bloons. Each pack of spikes \ncan pop 5 bloons before being used up. Use these to \nget bloons that escape past your towers. Cost: $30.";
			}
			else if (currentSelectedTowerButton.equals("explodingPineapple")) {
				return "Exploding Pineapple: Like all healthy food, pineapples explode violently \nshortly after being placed, so don't put any down \nuntil you want to blow up some bloons. Cost: $25.";
			}
		}
		
		return "";
	}

}
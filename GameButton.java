/*
	GameButton.java
 	Phillip Pham
 	Class used to create button like objects on the screen for user to click on to perform game actions.
*/

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class GameButton {
	//images for the different GameButtons
	private Image dartMonkeySelectImage = new ImageIcon("Sprites/Button Sprites/Dart_Monkey.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image tackShooterSelectImage = new ImageIcon("Sprites/Button Sprites/Tack_Shooter.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image sniperMonkeySelectImage = new ImageIcon("Sprites/Button Sprites/Sniper_Monkey.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image boomerangThrowerSelectImage = new ImageIcon("Sprites/Button Sprites/Boomerang_Thrower.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image ninjaMonkeySelectImage = new ImageIcon("Sprites/Button Sprites/Ninja_Monkey.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image bombTowerSelectImage = new ImageIcon("Sprites/Button Sprites/Bomb_Tower.png").getImage().getScaledInstance(50, 55, Image.SCALE_SMOOTH);
	private Image iceTowerSelectImage = new ImageIcon("Sprites/Button Sprites/Ice_Tower.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image glueGunnerSelectImage = new ImageIcon("Sprites/Button Sprites/Glue_Gunner.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image superMonkeySelectImage = new ImageIcon("Sprites/Button Sprites/Super_Monkey.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image monkeyApprenticeSelectImage = new ImageIcon("Sprites/Button Sprites/Monkey_Apprentice.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image bananaFarmSelectImage = new ImageIcon("Sprites/Button Sprites/Banana_Farm.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image spikeFactorySelectImage = new ImageIcon("Sprites/Button Sprites/Spike_Factory.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image roadSpikesSelectImage = new ImageIcon("Sprites/Button Sprites/Road_Spikes.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	private Image explodingPineappleSelectImage = new ImageIcon("Sprites/Button Sprites/Exploding_Pineapple.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	
	public Image fastForwardImage1 = new ImageIcon("Sprites/Button Sprites/Fast_Forward_1.png").getImage();
	public Image fastForwardImage2 = new ImageIcon("Sprites/Button Sprites/Fast_Forward_2.png").getImage();
	private Image homeImage = new ImageIcon("Sprites/Button Sprites/Home.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
	private Image goImage = new ImageIcon("Sprites/Button Sprites/Go.png").getImage();
	private Image sellImage = new ImageIcon("Sprites/Button Sprites/Sell.png").getImage();
	
	public int x; //x pos of button (top left corner)
	public int y; //y pos of button (top left corner)
	
	public String label;

	public Image buttonImageNormal;

	public int width;
	public int height;
	
	//CONSTRUCTOR
    public GameButton(String label, int x, int y) {
    	this.x = x;
    	this.y = y;
		this.label = label;
		
		//set fields depending on label of GameButton	
		if (label.equals("dartMonkey")) {
			buttonImageNormal = dartMonkeySelectImage;
			width = 50;
			height = 50;
		}
		else if (label.equals("tackShooter")) {
			buttonImageNormal = tackShooterSelectImage;
			width = 50;
			height = 50;
		}
		else if (label.equals("sniperMonkey")) {
			buttonImageNormal = sniperMonkeySelectImage;
			width = 50;
			height = 50;
		}
		else if (label.equals("boomerangThrower")) {
			buttonImageNormal = boomerangThrowerSelectImage;
			width = 50;
			height = 50;
		}
		else if (label.equals("ninjaMonkey")) {
			buttonImageNormal = ninjaMonkeySelectImage;
			width = 50;
			height = 50;
		}
		else if (label.equals("bombTower")) {
			buttonImageNormal = bombTowerSelectImage;
			width = 50;
			height = 50;
		}
		else if (label.equals("iceTower")) {
			buttonImageNormal = iceTowerSelectImage;
			width = 50;
			height = 50;
		}
		else if (label.equals("glueGunner")) {
			buttonImageNormal = glueGunnerSelectImage;
			width = 50;
			height = 50;
		}
		else if (label.equals("superMonkey")) {
			buttonImageNormal = superMonkeySelectImage;
			width = 50;
			height = 50;
		}
		else if (label.equals("monkeyApprentice")) {
			buttonImageNormal = monkeyApprenticeSelectImage;
			width = 50;
			height = 50;
		}
		else if (label.equals("bananaFarm")) {
			buttonImageNormal = bananaFarmSelectImage;
			width = 50;
			height = 50;
		}
		else if (label.equals("spikeFactory")) {
			buttonImageNormal = spikeFactorySelectImage;
			width = 50;
			height = 50;
		}
		else if (label.equals("roadSpikes")) {
			buttonImageNormal = roadSpikesSelectImage;
			width = 50;
			height = 50;
		}
		else if (label.equals("explodingPineapple")) {
			buttonImageNormal = explodingPineappleSelectImage;
			width = 50;
			height = 50;
		}
		else if (label.equals("fastForward")) {
			buttonImageNormal = fastForwardImage1;
			width = 105;
			height = 70; 
		}
		else if (label.equals("home")) {
			buttonImageNormal = homeImage;
			width = 25;
			height = 25;
		}
		else if (label.equals("go")) {
			buttonImageNormal = goImage;
			width = 100;
			height = 70;
		}
		else if (label.equals("sell")) {
			buttonImageNormal = sellImage;
			width = 100;
			height = 100;
		}

    }
	
	//getRect() returns the Rect of the GameButton
	public Rect getRect() {
		return new Rect(x, y, width, height);
	}
   
	//getImage() returns the respective button image
	public Image getImage() {
		return buttonImageNormal;
	}
}
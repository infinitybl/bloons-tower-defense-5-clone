/*  Bloon.java
 	Phillip Pham
	Class used to generate Bloon objects in the game that the Tower objects can shoot down.
*/

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class Bloon {
	//images for the different Bloon types
	private Image redBloonImage = new ImageIcon("Sprites/Balloon Sprites/Red_Bloon.png").getImage();
	private Image blueBloonImage = new ImageIcon("Sprites/Balloon Sprites/Blue_Bloon.png").getImage();
	private Image greenBloonImage = new ImageIcon("Sprites/Balloon Sprites/Green_Bloon.png").getImage();
	private Image yellowBloonImage = new ImageIcon("Sprites/Balloon Sprites/Yellow_Bloon.png").getImage();
	private Image pinkBloonImage = new ImageIcon("Sprites/Balloon Sprites/Pink_Bloon.png").getImage();
	private Image blackBloonImage = new ImageIcon("Sprites/Balloon Sprites/Black_Bloon.png").getImage();
	private Image whiteBloonImage = new ImageIcon("Sprites/Balloon Sprites/White_Bloon.png").getImage();
	private Image camoBloonImage = new ImageIcon("Sprites/Balloon Sprites/Camo_Bloon.png").getImage();
	private Image leadBloonImage = new ImageIcon("Sprites/Balloon Sprites/Lead_Bloon.png").getImage();
	private Image zebraBloonImage = new ImageIcon("Sprites/Balloon Sprites/Zebra_Bloon.png").getImage();
	private Image rainbowBloonImage = new ImageIcon("Sprites/Balloon Sprites/Rainbow_Bloon.png").getImage();
	private Image ceramicBloonImage = new ImageIcon("Sprites/Balloon Sprites/Ceramic_Bloon.png").getImage();
	private Image MOABBloonImage = new ImageIcon("Sprites/Balloon Sprites/MOAB_Bloon.png").getImage();
	private Image BFBBloonImage = new ImageIcon("Sprites/Balloon Sprites/BFB_Bloon.png").getImage();
	private Image ZOMGBloonImage = new ImageIcon("Sprites/Balloon Sprites/ZOMG_Bloon.png").getImage();
	
	//images for status effects
	private Image freezeImage = new ImageIcon("Sprites/Status Sprites/Freeze.png").getImage();
	private Image gluedImage = new ImageIcon("Sprites/Status Sprites/Glue.png").getImage();

	public double x; //x pos of the Bloon (top left)
	public double y; //y pos of the Bloon (top left)
	
	//Bloons stats
	public String bloonType;
	public int health;
	public int moneyValue;
	public double speed;
	public double width;
	public double height;

	public boolean isRemoved; //determines if the Bloon is removed
	public boolean isFrozen;
	public boolean isGlued;

	public boolean explosionImmunity;
	public boolean freezeImmunity;
	public boolean sharpImmunity;
	public boolean glueImmunity;
	public boolean detectionImmunity;
	
	//delay counter and tick to determine when Bloon loses freeze status effect
	private int freezeCounter;
	private final int freezeTick = 300;
	
	//delay counter and tick to determine when Bloon loses glued status effect
	private int glueCounter;
	private final int glueTick = 700;

	public int nodesPassed; //used to determine how much intersections Bloon has passed on the track
							//(for move() function)

	//CONSTUCTOR
	public Bloon(String bloonType, double x, double y, int nodesPassed) {
		this.bloonType = bloonType;
		this.x = x;
		this.y = y;
		this.nodesPassed = nodesPassed;

		isRemoved = false; //determines if the Bloon is removed
		isFrozen = false;
		isGlued = false;

		explosionImmunity = false;
		freezeImmunity = false;
		sharpImmunity = false;
		glueImmunity = false;
		detectionImmunity = false;

		freezeCounter = 0;
		
		speed = 2; //base speed
		
		//sets fields depending on bloonType
		if (bloonType.equals("red")) {
			health = 1;
			moneyValue = 2;
			speed *= 1;
			width = (double)redBloonImage.getWidth(null);
			height = (double)redBloonImage.getHeight(null);
		}
		else if (bloonType.equals("blue")) {
			health = 1;
			moneyValue = 3;
			speed *= 1.4;
			width = (double)blueBloonImage.getWidth(null);
			height = (double)blueBloonImage.getHeight(null);
		}
		else if (bloonType.equals("green")) {
			health = 1;
			moneyValue = 4;
			speed *= 1.8;
			width = (double)greenBloonImage.getWidth(null);
			height = (double)greenBloonImage.getHeight(null);
		}
		else if (bloonType.equals("yellow")) {
			health = 1;
			moneyValue = 5;
			speed *= 3.2;
			width = (double)yellowBloonImage.getWidth(null);
			height = (double)yellowBloonImage.getHeight(null);
		}
		else if (bloonType.equals("pink")) {
			health = 1;
			moneyValue = 6;
			speed *= 3.5;
			width = (double)pinkBloonImage.getWidth(null);
			height = (double)pinkBloonImage.getHeight(null);
		}
		else if (bloonType.equals("black")) {
			health = 1;
			moneyValue = 10;
			speed *= 1.8;
			width = (double)blackBloonImage.getWidth(null);
			height = (double)blackBloonImage.getHeight(null);
			explosionImmunity = true;
		}
		else if (bloonType.equals("white")) {
			health = 1;
			moneyValue = 10;
			speed *= 2;
			width = (double)whiteBloonImage.getWidth(null);
			height = (double)whiteBloonImage.getHeight(null);
			freezeImmunity = true;
		}
		else if (bloonType.equals("camo")) {
			health = 1;
			moneyValue = 1;
			speed *= 1;
			width = (double)camoBloonImage.getWidth(null);
			height = (double)camoBloonImage.getHeight(null);
			detectionImmunity = true;
		}
		else if (bloonType.equals("lead")) {
			health = 1;
			moneyValue = 12;
			speed *= 1;
			width = (double)leadBloonImage.getWidth(null);
			height = (double)leadBloonImage.getHeight(null);
			sharpImmunity = true;
		}
		else if (bloonType.equals("zebra")) {
			health = 1;
			moneyValue = 13;
			speed *= 1.8;
			width = (double)zebraBloonImage.getWidth(null);
			height = (double)zebraBloonImage.getHeight(null);
			explosionImmunity = true;
			freezeImmunity = true;
		}
		else if (bloonType.equals("rainbow")) {
			health = 1;
			moneyValue = 15;
			speed *= 2.2;
			width = (double)rainbowBloonImage.getWidth(null);
			height = (double)rainbowBloonImage.getHeight(null);
		}
		else if (bloonType.equals("ceramic")) {
			health = 10;
			moneyValue = 0;
			speed *= 2.5;
			width = (double)ceramicBloonImage.getWidth(null);
			height = (double)ceramicBloonImage.getHeight(null);
			glueImmunity = true;

		}
		else if (bloonType.equals("MOAB")) {
			health = 200;
			moneyValue = 200;
			speed *= 1;
			width = (double)MOABBloonImage.getWidth(null);
			height = (double)MOABBloonImage.getHeight(null);
			freezeImmunity = true;
			glueImmunity = true;
		}
		else if (bloonType.equals("BFB")) {
			health = 300;
			moneyValue = 300;
			speed *= 0.25;
			width = (double)BFBBloonImage.getWidth(null);
			height = (double)BFBBloonImage.getHeight(null);
			freezeImmunity = true;
			glueImmunity = true;
		}
		else if (bloonType.equals("ZOMG")) {
			health = 400;
			moneyValue = 400;
			speed *= 0.15;
			width = (double)ZOMGBloonImage.getWidth(null);
			height = (double)ZOMGBloonImage.getHeight(null);
			freezeImmunity = true;
			glueImmunity = true;
		}

		isRemoved = false;
	}

	//getImage() returns the respective image of the Bloon
	public Image getImage() {
		if (bloonType.equals("blue")) {
			return blueBloonImage;
		}
		else if (bloonType.equals("green")) {
			return greenBloonImage;
		}
		else if (bloonType.equals("yellow")) {
			return yellowBloonImage;
		}
		else if (bloonType.equals("pink")) {
			return pinkBloonImage;
		}
		else if (bloonType.equals("black")) {
			return blackBloonImage;
		}
		else if (bloonType.equals("white")) {
			return whiteBloonImage;
		}
		else if (bloonType.equals("camo")) {
			return camoBloonImage;
		}
		else if (bloonType.equals("lead")) {
			return leadBloonImage;
		}
		else if (bloonType.equals("zebra")) {
			return zebraBloonImage;
		}
		else if (bloonType.equals("rainbow")) {
			return rainbowBloonImage;
		}
		else if (bloonType.equals("ceramic")) {
			return ceramicBloonImage;
		}
		else if (bloonType.equals("MOAB")) {
			return MOABBloonImage;
		}
		else if (bloonType.equals("BFB")) {
			return BFBBloonImage;
		}
		else if (bloonType.equals("ZOMG")) {
			return ZOMGBloonImage;
		}
		else {
			return redBloonImage;
		}
	}
	
	//breakIntoBloons() adds more Bloon objects as certain Bloon types
	//break up into weaker Bloon(s) when popped   
	public ArrayList<Bloon> breakIntoBloons() {
		ArrayList<Bloon> bloonsProduced = new ArrayList<Bloon>();
		if (bloonType.equals("blue")) {
			bloonsProduced.add(new Bloon("red", x, y, nodesPassed));
		}
		else if (bloonType.equals("green")) {
			bloonsProduced.add(new Bloon("blue", x, y, nodesPassed));
		}
		else if (bloonType.equals("yellow")) {
			bloonsProduced.add(new Bloon("green", x, y, nodesPassed));
		}
		else if (bloonType.equals("pink")) {
			bloonsProduced.add(new Bloon("yellow", x, y, nodesPassed));
		}
		else if (bloonType.equals("black")) {
			bloonsProduced.add(new Bloon("pink", x, y, nodesPassed));
			bloonsProduced.add(new Bloon("pink", x, y, nodesPassed));
		}
		else if (bloonType.equals("white")) {
			bloonsProduced.add(new Bloon("pink", x, y, nodesPassed));
			bloonsProduced.add(new Bloon("pink", x, y, nodesPassed));
		}
		else if (bloonType.equals("camo")) {
			bloonsProduced.add(new Bloon("pink", x, y, nodesPassed));
		}
		else if (bloonType.equals("lead")) {
			bloonsProduced.add(new Bloon("black", x, y, nodesPassed));
			bloonsProduced.add(new Bloon("black", x, y, nodesPassed));
		}
		else if (bloonType.equals("zebra")) {
			bloonsProduced.add(new Bloon("black", x, y, nodesPassed));
			bloonsProduced.add(new Bloon("white", x, y, nodesPassed));
		}
		else if (bloonType.equals("rainbow")) {
			bloonsProduced.add(new Bloon("zebra", x, y, nodesPassed));
			bloonsProduced.add(new Bloon("zebra", x, y, nodesPassed));
		}
		else if (bloonType.equals("ceramic")) {
			bloonsProduced.add(new Bloon("rainbow", x, y, nodesPassed));
			bloonsProduced.add(new Bloon("rainbow", x, y, nodesPassed));
		}
		else if (bloonType.equals("MOAB")) {
			bloonsProduced.add(new Bloon("ceramic", x, y, nodesPassed));
			bloonsProduced.add(new Bloon("ceramic", x, y, nodesPassed));
		}
		else if (bloonType.equals("BFB")) {
			bloonsProduced.add(new Bloon("ceramic", x, y, nodesPassed));
			bloonsProduced.add(new Bloon("ceramic", x, y, nodesPassed));
		}
		else if (bloonType.equals("ZOMG")) {
			bloonsProduced.add(new Bloon("BFB", x, y, nodesPassed));
			bloonsProduced.add(new Bloon("BFB", x, y, nodesPassed));
		}
		
		//return Bloon list
		return bloonsProduced;
	}
	
	//move() moves the Bloon across the track with the Bloon direction
	//determined from how many intersections it passed (checked with Rect objects)
	public void move() {
		double movementSpeed = speed;
		//Bloon doesn't move if frozen
		if (isFrozen == true) {
			movementSpeed = 0;
		}
		//Bloon moves at half speed if glued
		if (isGlued == true) {
			movementSpeed = (int)(movementSpeed / 2);
		}

		if (nodesPassed == 0) {
			y += movementSpeed;
			if (getRect().overlaps(new Rect(683, 175, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 1) {
			x -= movementSpeed;
			if (getRect().overlaps(new Rect(560, 175, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 2) {
			y -= movementSpeed;
			if (getRect().overlaps(new Rect(562, 80, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 3) {
			x -= movementSpeed;
			if (getRect().overlaps(new Rect(70, 94, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 4) {
			y += movementSpeed;
			if (getRect().overlaps(new Rect(81, 285, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 5) {
			x += movementSpeed;
			if (getRect().overlaps(new Rect(205, 285, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 6) {
			y -= movementSpeed;
			if (getRect().overlaps(new Rect(205, 190, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 7) {
			x += movementSpeed;
			if (getRect().overlaps(new Rect(290, 193, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 8) {
			y += movementSpeed;
			if (getRect().overlaps(new Rect(290, 295, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 9) {
			x += movementSpeed;
			if (getRect().overlaps(new Rect(390, 295, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 10) {
			y -= movementSpeed;
			if (getRect().overlaps(new Rect(380, 194, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 11) {
			x += movementSpeed;
			if (getRect().overlaps(new Rect(480, 194, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 12) {
			y += movementSpeed;
			if (getRect().overlaps(new Rect(470, 385, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 13) {
			x += movementSpeed;
			if (getRect().overlaps(new Rect(595, 385, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 14) {
			y -= movementSpeed;
			if (getRect().overlaps(new Rect(595, 260, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 15) {
			x += movementSpeed;
			if (getRect().overlaps(new Rect(710, 260, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 16) {
			y += movementSpeed;
			if (getRect().overlaps(new Rect(710, 465, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 17) {
			x -= movementSpeed;
			if (getRect().overlaps(new Rect(340, 465, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 18) {
			y -= movementSpeed;
			if (getRect().overlaps(new Rect(352, 365, 5, 5))) {
				nodesPassed += 1;
			}
		}
		else if (nodesPassed == 19) {
			x -= movementSpeed;
		}
	}
	
	//checkStatusEffects() increases the respective status counters
	//if the Bloon is afflicted with a status effect and determines
	//when to remove it (when the tick is reached)
	public void checkStatusEffects() {
		if (isFrozen == true) {
			freezeCounter++;
			if (freezeCounter == freezeTick) {
				freezeCounter = 0;
				isFrozen = false;
			}
		}
		if (isGlued == true) {
			glueCounter++;
			if (glueCounter == glueTick) {
				glueCounter = 0;
				isGlued = false;
			}
		}
	}
	
	//paint() paints the Bloon on the screen
	public void paint(Graphics2D g2) {
		g2.drawImage(getImage(), (int)(x), (int)(y), null);
		if (isFrozen == true) {
			g2.drawImage(freezeImage, (int)(x), (int)(y), null);
		}
		else if (isGlued == true) {
			g2.drawImage(gluedImage, (int)(x), (int)(y), null);
		}
	}

	//getRect() gets the Rect of the Bloon
	public Rect getRect() {
		return new Rect((int)(x), (int)(y), (int)(width), (int)(height));
	}


}

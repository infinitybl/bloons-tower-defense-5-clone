/*  Tower.java
 	Phillip Pham
	Class used to generate Tower objects in the game that will shoot the Bloon objects.
*/

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Tower {
	//images for the different Tower types
	private Image dartMonkeyImage = new ImageIcon("Sprites/Tower Sprites/Dart_Monkey.png").getImage();
	private Image tackShooterImage = new ImageIcon("Sprites/Tower Sprites/Tack_Shooter.png").getImage();
	private Image sniperMonkeyImage = new ImageIcon("Sprites/Tower Sprites/Sniper_Monkey.png").getImage();
	private Image boomerangThrowerImage = new ImageIcon("Sprites/Tower Sprites/Boomerang_Thrower.png").getImage();
	private Image ninjaMonkeyImage = new ImageIcon("Sprites/Tower Sprites/Ninja_Monkey.png").getImage();
	private Image bombTowerImage = new ImageIcon("Sprites/Tower Sprites/Bomb_Tower.png").getImage();
	private Image iceTowerImage = new ImageIcon("Sprites/Tower Sprites/Ice_Tower.png").getImage();
	private Image glueGunnerImage = new ImageIcon("Sprites/Tower Sprites/Glue_Gunner.png").getImage();
	private Image superMonkeyImage = new ImageIcon("Sprites/Tower Sprites/Super_Monkey.png").getImage();
	private Image monkeyApprenticeImage = new ImageIcon("Sprites/Tower Sprites/Monkey_Apprentice.png").getImage();
	private Image bananaFarmImage = new ImageIcon("Sprites/Tower Sprites/Banana_Farm.png").getImage();
	private Image spikeFactoryImage = new ImageIcon("Sprites/Tower Sprites/Spike_Factory.png").getImage();
	private Image explodingPineappleImage = new ImageIcon("Sprites/Tower Sprites/Exploding_Pineapple.png").getImage();
	private Image roadSpikesImage = new ImageIcon("Sprites/Tower Sprites/Road_Spikes.png").getImage();
	
	//image to show pineapple Tower exploding
	private Image pineappleExplodingImage = new ImageIcon("Sprites/Status Sprites/Pineapple_Explosion.png").getImage();

	public int x; //x pos of the Tower (top left corner)
	public int y; //y pos of the Tower (top left corner)
	 
	
	//Tower stats
	public String towerType;
	public int attackPower;
	public int rangeRadius;
	public int width;
	public int height;
	public int costValue;
	public int sellValue;
	public int projectileSpeed;
	
	public int health; //only used for road spikes (remaining spikes)
	public int moneyRate; //only used for banana farm (money gained)
	
	public double angle; //angle in radians in which Tower is rotated from +ve y-axis counter clockwise

	public boolean isRemoved; //determines if Tower is removed
	public boolean isExploding; //determines if Tower is currently exploding (only for pineapple)
	
	//delay counter and tick to determine when Tower should perform an action
	public int attackDelayCounter;
	public int attackDelayTick;
	
	//delay counter and tick to determine when pineapple should be removed during exploding
	private int explosionDelayCounter;
	private final int explosionDelayTick = 5;
	
	//used to rotate Tower
	private AffineTransform saveXform;
	private AffineTransform at;
	 
	//SOUNDS
	private Sound explosionSmall = new Sound("Sounds/ExplosionSmall.wav");
	private Sound receivedCash = new Sound("Sounds/ReceivedCash.wav");
	private Sound woosh = new Sound("Sounds/Woosh.wav");
	private Sound glueSplatter = new Sound("Sounds/MOABDamage.wav");
	private Sound pop = new Sound("Sounds/Pop2.wav");

	//CONSTUCTOR
	public Tower(String towerType, int x, int y) {
		this.towerType = towerType;

		width = getImage().getWidth(null);
		height = getImage().getHeight(null);

		this.x = x;
		this.y = y;

		attackDelayCounter = 0;

		//sets fields depending on towerType
		if (towerType.equals("dartMonkey")) {
			attackPower = 1;
			rangeRadius = 86; 
			costValue = 200;
			sellValue = 159;
			attackDelayTick = 29;
			projectileSpeed = 5; //fast
		}
		else if (towerType.equals("tackShooter")) {
			attackPower = 1;
			rangeRadius = 72; //short
			costValue = 360;
			sellValue = 287;
			attackDelayTick = 50;
			projectileSpeed = 4; //slow
		}
		else if (towerType.equals("sniperMonkey")) {
			attackPower = 2;
			rangeRadius = 30; //infinite
			costValue = 350;
			sellValue = 279;
			attackDelayTick = 66;
			projectileSpeed = 10; //very fast
		}
		else if (towerType.equals("boomerangThrower")) {
			attackPower = 3;
			rangeRadius = 86; //medium
			costValue = 380;
			sellValue = 303;
			attackDelayTick = 40;
			projectileSpeed = 4;
		}
		else if (towerType.equals("ninjaMonkey")) {
			attackPower = 2;
			rangeRadius = 86;
			costValue = 500;
			sellValue = 399;
			attackDelayTick = 19;
			projectileSpeed = 6;
		}
		else if (towerType.equals("bombTower")) {
			attackPower = 20;
			rangeRadius = 86; 
			costValue = 650;
			sellValue = 519;
			attackDelayTick = 47;
			projectileSpeed = 4;
		}
		else if (towerType.equals("iceTower")) {
			attackPower = 0;
			rangeRadius = 58; //small
			costValue = 300;
			sellValue = 239;
			attackDelayTick = 73;
			projectileSpeed = 0;
		}
		else if (towerType.equals("glueGunner")) {
			attackPower = 0;
			rangeRadius = 86;
			costValue = 270;
			sellValue = 215;
			attackDelayTick = 31;
			projectileSpeed = 4;
		}
		else if (towerType.equals("superMonkey")) {
			attackPower = 1;
			rangeRadius = 150;
			costValue = 3500;
			sellValue = 2799;
			attackDelayTick = 10;
			projectileSpeed = 5;
		}
		else if (towerType.equals("monkeyApprentice")) {
			attackPower = 2;
			rangeRadius = 72;
			costValue = 550;
			sellValue = 439;
			attackDelayTick = 33;
			projectileSpeed = 4;
		}
		if (towerType.equals("bananaFarm")) {
			attackPower = 0;
			rangeRadius = 30;
			costValue = 1000;
			sellValue = 799;
			attackDelayTick = 500;
			projectileSpeed = 0;
			moneyRate = 80;
		}
		else if (towerType.equals("spikeFactory")) {
			attackPower = 0;
			rangeRadius = 86;
			costValue = 750;
			sellValue = 599;
			attackDelayTick = 500;
			projectileSpeed = 0;
		}
		else if (towerType.equals("roadSpikes")) {
			attackPower = 1;
			rangeRadius = 30;
			costValue = 30;
			attackDelayTick = 0;
			projectileSpeed = 0;
			health = 5;
		}
		else if (towerType.equals("explodingPineapple")) {
			attackPower = 20;
			rangeRadius = 60;
			costValue = 25;
			attackDelayTick = 33;
			projectileSpeed = 0;
		}


		isRemoved = false;
		isExploding = false;
	}

	//getImage() returns the respective Tower image
	public Image getImage() {
		if (towerType.equals("tackShooter")) {
			return tackShooterImage;
		}
		else if (towerType.equals("sniperMonkey")) {
			return sniperMonkeyImage;
		}
		else if (towerType.equals("boomerangThrower")) {
			return boomerangThrowerImage;
		}
		else if (towerType.equals("ninjaMonkey")) {
			return ninjaMonkeyImage;
		}
		else if (towerType.equals("bombTower")) {
			return bombTowerImage;
		}
		else if (towerType.equals("iceTower")) {
			return iceTowerImage;
		}
		else if (towerType.equals("bananaFarm")) {
			return bananaFarmImage;
		}
		else if (towerType.equals("glueGunner")) {
			return glueGunnerImage;
		}
		else if (towerType.equals("superMonkey")) {
			return superMonkeyImage;
		}
		else if (towerType.equals("monkeyApprentice")) {
			return monkeyApprenticeImage;
		}
		else if (towerType.equals("spikeFactory")) {
			return spikeFactoryImage;
		}
		else if (towerType.equals("explodingPineapple")) {
			return explodingPineappleImage;
		}
		else if (towerType.equals("roadSpikes")) {
			return roadSpikesImage;
		}
		else {
			return dartMonkeyImage;
		}
	}
	
	//checkIfCanAddProjectile() checks if Tower can shoot a Projectile 
	public ArrayList<Projectile> checkIfCanAddProjectile(ArrayList<Bloon> bloonsList, ArrayList<Projectile> projList) {
		boolean bloonInRadius = false; //used to check if a Bloon is within the rangeRadius of the Tower
		int shortestDistance = Integer.MAX_VALUE; //shortest distance b/w the closest Bloon and the Tower
		int shortestDistanceX = 0; //x coor of the above mentioned Bloon
		int shortestDistanceY = 0; //y coor of the above mentioned Bloon
		int checkDistance; //used to check for the smallest distance
		
		//check all Bloon objects on screen 
		for (Bloon b : bloonsList) {
			//if the Bloon can be detected normally or the Tower is a ninja monkey (ninja monkey can detect camo bloons),
			//check if it's the the new shortestDistance, and update respective variables if it is
			if (b.detectionImmunity == false || towerType.equals("ninjaMonkey")) {
				checkDistance = (this.x - (int)(b.x + b.width / 2)) * (this.x - (int)(b.x + b.width / 2)) + (this.y - (int)(b.y + b.height / 2)) * (this.y - (int)(b.y + b.height / 2));
				//sniper monkeys can shoot anywhere on map, no need to check within rangeRadius
				if (towerType.equals("sniperMonkey")) {
					if (checkDistance < shortestDistance) {
						shortestDistance = checkDistance;
						shortestDistanceX = (int)(b.x + b.width / 2);
						shortestDistanceY = (int)(b.y + b.height / 2);
					}
				}
				else if (checkDistance < (rangeRadius * rangeRadius)) {
					bloonInRadius = true;
					if (checkDistance < shortestDistance) {
						shortestDistance = checkDistance;
						shortestDistanceX = (int)(b.x + b.width / 2);
						shortestDistanceY = (int)(b.y + b.height / 2);
					}

				}
			}

		}
		

		if (towerType.equals("sniperMonkey") || bloonInRadius == true) {
			attackDelayCounter++;
			//if the delay counter reached the tick time
			if (attackDelayCounter == attackDelayTick) {
				attackDelayCounter = 0; //reset the counter
				//rotate the Tower and add a new Projectile
				if (shortestDistanceX != 0 && shortestDistanceY != 0) {
					rotateTower(shortestDistanceX, shortestDistanceY);
					projList = shootProjectile(projList);
				}
				if (towerType.equals("ninjaMonkey")) {
					woosh.play();
				}
				else if (towerType.equals("glueGunner")) {
					glueSplatter.play();
				}
			}
		}
		
		//return the Projectile list
		return projList;
	}
	
	//checkIfCanAddRoadSpikes() is used for the spike factory Tower to check if it 
	//can add road spikes to the screen 
	public ArrayList<Tower> checkIfCanAddRoadSpikes(ArrayList<Tower> towersList) {
		//get random x and y coor within rangeRadius and add new road spike Tower
		
		int randomCircleX = (int)((Math.random() * ((x + rangeRadius - 50) - (x - rangeRadius + 50)) + 1) + (x - rangeRadius + 50));
		int randomCircleY = (int)((Math.random() * ((y + rangeRadius - 50) - (y - rangeRadius + 50)) + 1) + (y - rangeRadius + 50));
		
		attackDelayCounter++;	
		if (attackDelayCounter == attackDelayTick) {
			attackDelayCounter = 0;
			towersList.add(new Tower("roadSpikes", randomCircleX, randomCircleY));
		}

		return towersList;
	}
	
	//checkIfCanAffectBloon() is used for the ice tower, road spikes, and exploding pineapple
	//Tower to check if it directly affects the Bloons
	public ArrayList<Bloon> checkIfCanAffectBloon(ArrayList<Bloon> bloonsList) {
		//ice tower freezes any Bloon in its range
		if (towerType.equals("iceTower")) {
			attackDelayCounter++;
			if (attackDelayCounter == attackDelayTick) {
				attackDelayCounter = 0;
				for (Bloon b : bloonsList) {
					if ((x - b.x) * (x - b.x) + (y - b.y) * (y - b.y) < (rangeRadius * rangeRadius) && b.freezeImmunity == false) {
						b.isFrozen = true; //set isFrozen field of the Bloon to true
					}
				}
			}
		}
		
		//road spikes directly pop Bloons
		else if (towerType.equals("roadSpikes")) {
			for (Bloon b : bloonsList) {
				//can only affect Bloons that don't have sharpness immunity
				if (getRect().overlaps(b.getRect()) && b.sharpImmunity == false) {
					health -= 1;
					b.health -= attackPower;
					//remove game objects if necessary
					if (health <= 0) {
						isRemoved = true;
					}
					if (b.health <= 0) {
						b.isRemoved = true;
					}
					pop.play();
				}
			}
		}
		//exploding pineapple damage Bloons within its range
		else if (towerType.equals("explodingPineapple")) {
			attackDelayCounter++;
			if (attackDelayCounter == attackDelayTick) {
				isExploding = true;
				Rect explosionRect = new Rect((int)(x - pineappleExplodingImage.getWidth(null) / 2), (int)(y - pineappleExplodingImage.getHeight(null) / 2), pineappleExplodingImage.getWidth(null), pineappleExplodingImage.getHeight(null));
				for (Bloon b : bloonsList) {
					//can only affect Bloons that don't have explosion immunity
					if (explosionRect.overlaps(b.getRect()) && b.explosionImmunity == false) {
						b.health -= attackPower;
						if (b.health <= 0) {
							b.isRemoved = true;
						}
					}
				}
				explosionSmall.play();
			}
		}
		
		//return the Bloons list
		return bloonsList;
	}
	
	//rotateTower() calculates the angle in radians the Tower is rotated counter clockwise from the -ve y axis
	//depending on the x and y coors of the nearest Bloon
	public void rotateTower(int shortestDistanceX, int shortestDistanceY) {
	
		if (!towerType.equals("tackShooter") && !towerType.equals("iceTower") &&
			!towerType.equals("spikeFactory") && !towerType.equals("explodingPineapple")
			&& !towerType.equals("roadSpikes")) {
			
			//add PI over 2 to tan inverse as Tower image is already rotated PI over 2 counter clockwise from +ve x axis
			angle = Math.PI / 2 + Math.atan2(shortestDistanceY - y, shortestDistanceX - x);
		}

	}
	
	//shootProjectile() adds a Projectile object(s) to the game depending on the respective Tower
	public ArrayList<Projectile> shootProjectile(ArrayList<Projectile> projList) {
		if (towerType.equals("dartMonkey")) {
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, angle));
		}
		if (towerType.equals("tackShooter")) {
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, 360));
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, 45));
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, 90));
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, 135));
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, 180));
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, 225));
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, 270));
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, 315));
		}
		else if (towerType.equals("sniperMonkey")) {
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, angle));
		}
		else if (towerType.equals("boomerangThrower")) {
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, angle));
		}
		else if (towerType.equals("ninjaMonkey")) {
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, angle));
		}
		else if (towerType.equals("bombTower")) {
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, angle));
		}
		else if (towerType.equals("glueGunner")) {
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, angle));
		}
		else if (towerType.equals("superMonkey")) {
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, angle));
		}
		else if (towerType.equals("monkeyApprentice")) {
			projList.add(new Projectile(towerType, x, y, attackPower, projectileSpeed, rangeRadius, angle));
		}
		
		//return the Projectile List
		return projList;
	}
	
	//checkIfCanAddMoney() is used to check if the bananaFarm can increase the player's money
	public int checkIfCanAddMoney(int money) {
		if (towerType.equals("bananaFarm")) {
			attackDelayCounter++;
			if (attackDelayCounter == attackDelayTick) {
				attackDelayCounter = 0;
				money += moneyRate;
				receivedCash.play();
			}
		}

		return money;

	}
	
	//checkIfCanAddMoney() is used to check when the pineapple Tower should
	//be removed during explosion
	public void checkIfExploding() {
		if (towerType.equals("explodingPineapple") && isExploding == true) {
			explosionDelayCounter++;
			if (explosionDelayCounter == explosionDelayTick) {
				isRemoved = true;
			}
		}
	}
	
	//paint() draws the Tower on the screen
	public void paint(Graphics2D g2) {
		//used to rotate the Tower
		saveXform = g2.getTransform();
		at = new AffineTransform();

		at.rotate(angle, x, y);
		g2.transform(at); //rotate g2 so that if renders Tower image at an angle
		
		//if pineapple Tower is currently exploding, draw explosion image 
		if (towerType.equals("explodingPineapple") && isExploding == true) {
			g2.drawImage(pineappleExplodingImage, (int)(x - pineappleExplodingImage.getWidth(null) / 2), (int)(y - pineappleExplodingImage.getHeight(null) / 2), null);
		}
		//otherwise, draw respective Tower
		else {
			g2.drawImage(getImage(), x - (int)(width / 2), y - (int)(height / 2), null);
		}
	
		g2.setTransform(saveXform); //reset rotation back to normal for g2

	}
	
	//get center coors of Tower
	public int getCenterX() {
		return (int)(x + width / 2);
	}

	public int getCenterY() {
		return (int)(y + height / 2);
	}

	//getRect() gets the Rect of the Tower
	public Rect getRect() {
		return new Rect((int)(x - width / 2), (int)(y - height / 2), width, height);
	}


}

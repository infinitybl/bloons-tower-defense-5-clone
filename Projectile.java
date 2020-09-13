/*  Projectile.java
 	Phillip Pham
	Class used to generate Projectile objects in the game that are shooted by the Tower objects to pop the Bloon objects.
*/

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Projectile {
	//images for the different Projectile types
	private Image dartMonkeyImage = new ImageIcon("Sprites/Projectile Sprites/Dart_Monkey.png").getImage();
	private Image tackShooterImage = new ImageIcon("Sprites/Projectile Sprites/Tack_Shooter.png").getImage();
	private Image sniperMonkeyImage = new ImageIcon("Sprites/Projectile Sprites/Sniper_Monkey.png").getImage();
	private Image boomerangThrowerImage = new ImageIcon("Sprites/Projectile Sprites/Boomerang_Thrower.png").getImage();
	private Image ninjaMonkeyImage = new ImageIcon("Sprites/Projectile Sprites/Ninja_Monkey.png").getImage();
	private Image bombTowerImage = new ImageIcon("Sprites/Projectile Sprites/Bomb_Tower.png").getImage();
	private Image glueGunnerImage = new ImageIcon("Sprites/Projectile Sprites/Glue_Gunner.png").getImage();
	private Image superMonkeyImage = new ImageIcon("Sprites/Projectile Sprites/Super_Monkey.png").getImage();
	private Image monkeyApprenticeImage = new ImageIcon("Sprites/Projectile Sprites/Monkey_Apprentice.png").getImage();
	
	//image to show bomb Projectile exploding
	private Image bombExplodingImage = new ImageIcon("Sprites/Status Sprites/Bomb_Explosion.png").getImage();
	
	public int x; //x pos of the Projectile (top left corner)
	public int y; //y pos of the Projectile (top left corner)
	
	//Projectile stats
	public String projectileType;
	public int attack;
	public int width;
	public int height;
	public int speed;
	public int rangeRadius;
	public int distanceTravelled;
	public double angle;
	public int health;

	public boolean isRemoved; //determines if Projectile is removed
	public boolean isExploding; //determines if Projectile is currently exploding (only for bomb)
	
	//delay counter and tick to determine when bomb should be removed during explosion
	private int explosionDelayCounter;
	private final int explosionDelayTick = 5;
	
	//used to rotate Projectile
	private AffineTransform saveXform;
	private AffineTransform at;
	
	//SOUNDS
	private Sound ceramicBloonHit = new Sound("Sounds/CeramicBloonHit.wav");
	private Sound explosionBig = new Sound("Sounds/ExplosionBig.wav");
	private Sound frozenBloonHit = new Sound("Sounds/FrozenBloonHit.wav");
	private Sound metalBloonHit = new Sound("Sounds/MetalBloonHit.wav");
	private Sound MOABDamage = new Sound("Sounds/MOABDamage.wav");
	private Sound glueStrike = new Sound("Sounds/GlueStrike.wav");
	private Sound pop = new Sound("Sounds/Pop1.wav");
	
	

	//CONSTUCTOR
	public Projectile(String projectileType, int x, int y, int attack, int speed, int rangeRadius, double angle) {
		this.x = x;
		this.y = y;
		this.projectileType = projectileType;
		this.attack = attack;
		this.speed = speed;
		this.rangeRadius = rangeRadius;
		this.angle = angle;
		
		width = getImage().getWidth(null);
		height = getImage().getHeight(null);
		distanceTravelled = 0;
		
		//set how many Bloons Projectile can pop
		health = 1;
		if (projectileType.equals("boomerangThrower")) {
			health = 3;
		}
		else if (projectileType.equals("monkeyApprentice")) {
			health = 2;
		}

		isRemoved = false;
		isExploding = false;

	}

	//getImage() returns the respective Projectile image
	public Image getImage() {
		if (projectileType.equals("tackShooter")) {
			return tackShooterImage;
		}
		else if (projectileType.equals("sniperMonkey")) {
			return sniperMonkeyImage;
		}
		else if (projectileType.equals("boomerangThrower")) {
			return boomerangThrowerImage;
		}
		else if (projectileType.equals("ninjaMonkey")) {
			return ninjaMonkeyImage;
		}
		else if (projectileType.equals("bombTower")) {
			return bombTowerImage;
		}
		else if (projectileType.equals("glueGunner")) {
			return glueGunnerImage;
		}
		else if (projectileType.equals("superMonkey")) {
			return superMonkeyImage;
		}
		else if (projectileType.equals("monkeyApprentice")) {
			return monkeyApprenticeImage;
		}
		else {
			return dartMonkeyImage;
		}
	}
	
	//move() moves the Projectile
	public void move() {
		//moving the boomerang Projectile on the way back
		if (projectileType.equals("boomerangThrower") && distanceTravelled > rangeRadius) {
			x -= speed * Math.cos(angle - Math.PI / 2);
			y -= speed * Math.sin(angle - Math.PI / 2);
		}
		//moving any other Projectile normally
		else {
			x += speed * Math.cos(angle - Math.PI / 2);
			y += speed * Math.sin(angle - Math.PI / 2);

		}

		distanceTravelled += speed; 
		
		//remove boomerang Projectile after if travelled back
		if (projectileType.equals("boomerangThrower") && distanceTravelled > 2 * rangeRadius) {
			isRemoved = true;
		}
		//remove any other Projectile after if travelled past the range radius (sniper monkey has infinite range)
		else if (!projectileType.equals("boomerangThrower") && !projectileType.equals("sniperMonkey") && distanceTravelled > rangeRadius) {
			isRemoved = true;
		}
		//remove sniper monkey Projectile if it reached end of track
		if (!getRect().isInside(new Rect(0 - 50, 0 - 50, 775 + 50, 570 + 50))) {
			isRemoved = true;
		}
	}
	
	//checkIfExploding() is used to check when the bomb Projectile should
	//be removed during explosion
	public void checkIfExploding() {
		if (projectileType.equals("bombTower") && isExploding == true) {
			explosionDelayCounter++;
			if (explosionDelayCounter == explosionDelayTick) {
				isRemoved = true;
			}
		}
	}
	
	//checkForCollision() is used to check when the if the Projectile collided 
	//with any Bloon objects
	public ArrayList<Bloon> checkForCollision(ArrayList<Bloon> bloonsList) {
		for (Bloon b : bloonsList) {
			if (getRect().overlaps(b.getRect())) {
				//slow down Bloon for glue Projectile
				if (projectileType.equals("glueGunner") && b.glueImmunity == false) {
					b.isGlued = true;
					// glueStrike.play();
				}
				//damage all Bloons within range for bomb Projectile
				else if (projectileType.equals("bombTower") && b.explosionImmunity == false) {
					isExploding = true;
					Rect bombRect = new Rect((int)(x - bombExplodingImage.getWidth(null) / 2), (int)(y - bombExplodingImage.getHeight(null) / 2), bombExplodingImage.getWidth(null), bombExplodingImage.getHeight(null));
					if (bombRect.overlaps(b.getRect())) {
						b.health -= attack;
						if (b.health <= 0) {
							b.isRemoved = true;
						}
					}
					explosionBig.play();
				}
				else if (projectileType.equals("dartMonkey") || projectileType.equals("tackShooter") || projectileType.equals("sniperMonkey")
						|| projectileType.equals("boomerangThrower") || projectileType.equals("ninjaMonkey")) {
					//frozen Bloons are always affected by any Projectiles
					if (b.sharpImmunity == false || b.isFrozen == true) {
						health -= 1;
						b.health -= attack;
						if (health <= 0) {
							isRemoved = true;
						}
						if (b.health <= 0) {
							b.isRemoved = true;
						}
					}
					
				}
				//Projectiles from super monkey and monkey apprentice can bypass sharp immunity
				else if (projectileType.equals("superMonkey") || projectileType.equals("monkeyApprentice")) {
					health -= 1;
					b.health -= attack;
					if (health <= 0) {
						isRemoved = true;
					}
					if (b.health <= 0) {
						b.isRemoved = true;
					}
				}
				
				if (projectileType.equals("dartMonkey") || projectileType.equals("tackShooter") || projectileType.equals("sniperMonkey")
					|| projectileType.equals("boomerangThrower") || projectileType.equals("ninjaMonkey") || projectileType.equals("superMonkey")
					|| projectileType.equals("monkeyApprentice")) {
					if (b.isRemoved == true) {
						if (!b.bloonType.equals("lead") && !b.bloonType.equals("ceramic") && !b.bloonType.equals("MOAB")
							&& !b.bloonType.equals("BFB") && !b.bloonType.equals("ZOMG")) {
							pop.play();	
						}
					}
					else if (b.isFrozen == true) {
						frozenBloonHit.play();
					}
					else if (b.bloonType.equals("ceramic")) {
						ceramicBloonHit.play();
					}
					else if (b.bloonType.equals("lead")) {
						metalBloonHit.play();
					}
					else if (b.bloonType.equals("MOAB") || b.bloonType.equals("BFB") || b.bloonType.equals("ZOMG")) {
						MOABDamage.play();
					}
					
					
				}
				
			}
			
		}
		
		//return Bloon list
		return bloonsList;
	}
	
	//paint() draws the Bloon on the screen
	public void paint(Graphics2D g2) {
		saveXform = g2.getTransform();
		at = new AffineTransform();

		at.rotate(angle, x, y);
		g2.transform(at);

		if (projectileType.equals("bombTower") && isExploding == true) {
			g2.drawImage(bombExplodingImage, (int)(x - bombExplodingImage.getWidth(null) / 2), (int)(y - bombExplodingImage.getHeight(null) / 2), null);
		}
		else {
			g2.drawImage(getImage(), x - (int)(width / 2), y - (int)(height / 2), null);
		}

		g2.setTransform(saveXform);
	}


	//getRect() gets the Rect of the Projectile
	public Rect getRect() {
		return new Rect(x, y, width, height);
	}


}

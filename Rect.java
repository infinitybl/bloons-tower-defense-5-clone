/*  Rect.java
 	Phillip Pham
	Class used to create Rect objects that model rectangles.
*/
import java.util.*;

public class Rect {
	private int x; //x pos of the Rect (top left corner)
	private int y; //x pos of the Rect (top left corner)
	private int width; //width of the Rect
	private int height; //height of the Rect
	
	//CONSTRUCTOR
	public Rect(int xCoor, int yCoor, int widthValue, int heightValue) {
		x = xCoor;
		y = yCoor;
		width = widthValue;
		height = heightValue;
		
	}
	
	//methods to return properties about the Rect
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	//overlaps() checks if the Rect collided with the parameter Rect
	public boolean overlaps(Rect rectangle) {
		if (x < rectangle.x + rectangle.width &&
			x + width > rectangle.x &&
			y < rectangle.y + rectangle.height &&
			y + height > rectangle.y) {
			return true;
		}
		return false;
	}
	
	//isInside() checks if the Rect is completely inside the parameter Rect
	public boolean isInside(Rect rectangle) {
		if (x > rectangle.x && x + width < rectangle.x + rectangle.width &&
			y > rectangle.y && y + height < rectangle.y + rectangle.height) {
			return true;
		}
		return false;
	}
	
	//contains checks if a point is inside the Rect
	public boolean contains(int mx, int my) {
		if (mx < x + width && mx > x && my < y + height && my > y) {
			return true;
		}
		return false;
    }

	
}
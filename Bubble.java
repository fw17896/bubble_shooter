package bubble_shooter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

// realises the bubbles of the game
public class Bubble {
	Color color;
	public static final int RADIUS = 14;
	private boolean visible;
	Point loc;
	private boolean marked;
	
	
	// constructor
	public Bubble(Color c){
		color = c;
		marked = false;
	}
	
	// returns the y coordinate of the bubble
	public int getRow(){
		return loc.y/(Constants.ROW_DISTANCE/2);
	}
	
	// returns the x coordinate of the bubble
	public int getCol(){
		return loc.x/((Bubble.RADIUS+1)*2);
	}
	
	// marks the bubble
	public void mark(){
		marked=true;
	}
	
	// unmarks the bubble
	public void unmark(){
		marked = false;
	}
	
	// returns if the bubble is marked
	public boolean isMarked(){
		return marked;
	}
	
	// getter for the color of the bubble
	public Color getColor(){
		return color;
	}
	
	// setter for visiblity
	public void setVisible(boolean v){
		visible = v;
	}
	
	// getter for visibility
	public boolean isVisible(){
		return visible;
	}
	
	// setter for the location of the bubble
	public void setLocation(Point p){
		this.loc=p;
	}
	
	// getter for the location of the bubble
	public Point getLocation(){
		return loc;
	}
	
	// getter for the location of the center of the bubble
	public Point getCenterLocation(){
		return new Point(loc.x+RADIUS+1, loc.y+RADIUS+1);
	}
	
	// paints the bubble on the given object with the specified color to the specified location
	public void paintBubble(Graphics2D g2d){
		if(isVisible()){
			g2d.setColor(color);
			g2d.fillOval(loc.x, loc.y, RADIUS*2, RADIUS*2);
		}
	}
	
	// gets a random color that can be set as the color of a bubble
	public static Color getRandomColor(int bound){
		int rnd = (int) (bound<=8 ? Math.random()*bound : Math.random()*8);
		switch (rnd) {
		case 0:
			return Color.blue;
		case 1:
			return Color.red;
		case 2:
			return Color.yellow;
		case 3:
			return Color.green;
		case 4:
			return Color.cyan;
		case 5:
			return Color.magenta;
		case 6:
			return Color.orange;
		case 7:
			return Color.darkGray;
		default:
			break;
		} 
		return null;
	}
	
}

package bubble_shooter;

import java.awt.*;
import java.awt.geom.*;

//  Realises the moving arrow at the bottom of the screen.
public class Arrow{

	private Point p;
	
	private static final int TIP_LENGTH = 20;
	private static final int LENGTH = 80;
	private static final int SHAFT_WIDTH = 6;

    // Constructor, initializes the arrow's point to the center bottom of the field
	public Arrow(){
		p = new Point(Constants.FIELD_SIZE_X/2,0);
	}
	
    // Draws the arrow on the screen pointing toward the mouse location
    public void paintComponent(Graphics2D g2d, Point base) {
    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    	Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
    	int x = mouseLoc.x-base.x;
        int y = mouseLoc.y-base.y;

    	if((0<=x) && (x<Constants.FIELD_SIZE_X) && (0<=y) && (y<Constants.FIELD_SIZE_Y)){
    		p=mouseLoc;
    	}

    	x = p.x-base.x;
        y = p.y-base.y;

		int centerX = Constants.FIELD_SIZE_X / 2;
        int baseY = Constants.FIELD_SIZE_Y;

        double angle = Math.atan((double)(x - centerX) / (baseY - y));

        AffineTransform old = g2d.getTransform();

        g2d.translate(centerX, baseY);
        g2d.rotate(angle);

        GradientPaint gradient = new GradientPaint(0, 0, Color.RED, 0, -LENGTH, Color.DARK_GRAY, true);
        g2d.setPaint(gradient);
        g2d.fillRect(-SHAFT_WIDTH / 2, -LENGTH, SHAFT_WIDTH, LENGTH);

        Polygon head = new Polygon();
        head.addPoint(0, -LENGTH - TIP_LENGTH);        
        head.addPoint(-TIP_LENGTH / 2, -LENGTH);      
        head.addPoint(TIP_LENGTH / 2, -LENGTH);             

        g2d.setColor(Color.RED);
        g2d.fillPolygon(head);
        g2d.setTransform(old);
    }

}

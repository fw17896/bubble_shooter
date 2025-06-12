package bubble_shooter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

// the engine of the game
public class Game implements ActionListener{
	private ArrayList<RowList> bubbles;
	private LinkedList<Bubble> upcoming;
	private MovingBubble moving_bubble;
	private int initial_rows;
	private int colors;
	private Timer timer;
	private Canvas canvas;
	private int shotCount;
	private int numOfBubbles;
	private MainFrame mainFrame;
	private long score;
	private boolean stopped;
	public static final int ROW_COUNT = 12;
	public static final int COL_COUNT_FULL = 43;
	public static final int COL_COUNT = 42;
	public static final int SCORE_SHOT = 10;
	public static final int SCORE_COHERENT = 20;
	public static final int SCORE_FLOATING = 40;

	
	// contructor for a new game. initalises the bubble matrix, the upcoming bubbles and other parameters
	public Game(int row, int colors, Canvas c){
		canvas = c;
		stopped = false;
		initial_rows = row;
		this.colors = colors;
		shotCount = 0;
		numOfBubbles = 0;
		score = 0;
		bubbles = new ArrayList<RowList>();
		for(int i = 0; i<ROW_COUNT; i++){
			RowList r = new RowList((i%2==0 ? true : false));
			bubbles.add(r);
			for(int j=0; j<(r.isFull() ? 43 : 42); j++){
				
				Bubble b = new Bubble(Bubble.getRandomColor(colors));
				b.setLocation(
					new Point(r.isFull() ?
						       j*2*(Bubble.RADIUS+1) :
							   j*2*(Bubble.RADIUS+1)+(Bubble.RADIUS+1),
							  r.isFull() ?
							   (i/2)*Constants.ROW_DISTANCE :
							   (i/2)*Constants.ROW_DISTANCE+Constants.ROW_DISTANCE/2));
				r.add(b);
				if(i<initial_rows){
					b.setVisible(true);
					numOfBubbles++;
				}	
				else
					b.setVisible(false);
			}
		}
		
		upcoming = new LinkedList<Bubble>();
		for(int i=0; i<4; i++){
			Bubble b = new Bubble(Bubble.getRandomColor(colors));
			upcoming.add(b);
		}
		arrangeUpcoming();
	}
	
	// setter for the mainframe
	public void setMainFrame(MainFrame m){
		mainFrame = m;
	}
	
	// paints the bubbles on the screen
	public void paintBubbles(Graphics2D g2d){
		for(RowList r : bubbles){
			for(Bubble b : r){
				b.paintBubble(g2d);
			}
		}
		for(Bubble b : upcoming){
			b.paintBubble(g2d);
		}
		if (moving_bubble!=null)
			moving_bubble.paintBubble(g2d);
	}
	
	// arrange the bubble when bubble is removed and add
	private void arrangeUpcoming(){
		upcoming.element().setLocation(
				new Point(Constants.FIELD_SIZE_X/2-Bubble.RADIUS,
				          Constants.FIELD_SIZE_Y-Bubble.RADIUS));
		upcoming.element().setVisible(true);
		for (int i=1; i<4; i++){
			upcoming.get(i).setLocation(new Point(
					Constants.FIELD_SIZE_X-(4-i)*(2*(Bubble.RADIUS+6)),
					Constants.FIELD_SIZE_Y-(Bubble.RADIUS+1)));
			upcoming.get(i).setVisible(true);
		}
	}
		
	// fires the bubble
	public void fire(Point mouseLoc, Point panelLoc){
		boolean movingExists = !(moving_bubble==null);
		movingExists = (movingExists ? moving_bubble.isMoving() : false);
		if(!movingExists && mouseLoc.y < ( Constants.FIELD_SIZE_Y + 8)) {
			Point dir = new Point(mouseLoc.x-panelLoc.x, mouseLoc.y-panelLoc.y);
			moving_bubble = new MovingBubble(upcoming.remove(),dir);
			playSoundEffect("C:\\\\Users\\\\FATIMA WASEEM\\\\OneDrive - Higher Education Commission\\\\Desktop\\\\bubble_shooter-master1\\\\bubble_shooter\\float.wav");
			upcoming.add(new Bubble(Bubble.getRandomColor(colors)));
			arrangeUpcoming();
			numOfBubbles++;
			score+=SCORE_SHOT;
			mainFrame.updateScore(score);
			timer = new Timer(20, this);
			timer.start();
		}
	}

	// checks whether the moving bubble is close to a fixed one if yes, then it will be fixed in the hexile grid
	public void checkProximity(){
		int currentPosX = moving_bubble.getCenterLocation().x;
		int currentPosY = moving_bubble.getCenterLocation().y;
		int row = (currentPosY-Bubble.RADIUS)/(Constants.ROW_DISTANCE/2);
		int col;
		if (row < ROW_COUNT){
			if (bubbles.get(row).isFull()){
				col = (currentPosX)/((Bubble.RADIUS+1)*2);
			}
			else{
				col = (currentPosX-(Bubble.RADIUS+1))/((Bubble.RADIUS+1)*2);
			}
			if(row == 0){
				fixBubble(row, col);
			}
			ArrayList<Bubble> neighbours = getNeighbours(row, col);
			for (Bubble b : neighbours){
				if (b.isVisible() && BubbleDist(moving_bubble, b)<=4+(Bubble.RADIUS+1)*2){
					fixBubble(row, col);
					break;
				}
			}
		}
	}
	
	// places the moving bubble in the grid on the given position
	private void fixBubble(int row, int col){
		Point temp_point = bubbles.get(row).get(col).getLocation();
		moving_bubble.setLocation(temp_point);
		bubbles.get(row).set(col, moving_bubble);
		timer.stop();
		moving_bubble.setMoving(false);
		int removed = removeCoherent(row, col) + removeFloating();
		mainFrame.updateScore(score);
		numOfBubbles-=removed;
		if(removed == 0){
			shotCount++;
		}
		if(shotCount == 5){
			shotCount = 0;
			addRow();
		}
		canvas.repaint();
		if(numOfBubbles == 0){
			stop();
			score*=1.2;
			mainFrame.gameWon(score);
		}
		for(Bubble b : bubbles.get(ROW_COUNT-1)){
			if(b.isVisible()){
				stop();
				score*=0.8;
				mainFrame.gameLost(score);
				break;
			}
		}
	}
	
	// adds a new row on the top of the field
	private void addRow(){
		bubbles.remove(ROW_COUNT-1);
		for (RowList r : bubbles){
			for (Bubble b : r){
				b.setLocation(new Point(b.getLocation().x,
	                b.getLocation().y+Constants.ROW_DISTANCE/2));
			}
		}

		RowList newRow = new RowList(!bubbles.get(0).isFull());
		for (int i = 0; i< (newRow.isFull() ? 43 : 42); i++){
			Bubble b = new Bubble(Bubble.getRandomColor(colors));
			b.setLocation(
				new Point((newRow.isFull() ?
					       i*2*(Bubble.RADIUS+1) :
						   i*2*(Bubble.RADIUS+1)+(Bubble.RADIUS+1)),0));
			b.setVisible(true);
			newRow.add(b);
			numOfBubbles++;
		}
		bubbles.add(0,newRow);
	}
	
	// returns the neighbours of a bubble in the grid
	private ArrayList<Bubble> getNeighbours(int row, int col){
		ArrayList<Bubble> neighbours = new ArrayList<Bubble>();
		//LEFT
		if (col>0) neighbours.add(bubbles.get(row).get(col-1));
		//RIGHT
		if (col < (bubbles.get(row).isFull() ? COL_COUNT_FULL : COL_COUNT)-1){
			neighbours.add(bubbles.get(row).get(col+1));
		}
		//UPPER LEFT
		if (bubbles.get(row).isFull() && col > 0 && row > 0){
			neighbours.add(bubbles.get(row-1).get(col-1));
		}
		if (!bubbles.get(row).isFull() && row > 0){
			neighbours.add(bubbles.get(row-1).get(col));
		}
		//UPPER RIGHT
		if (bubbles.get(row).isFull() && col < COL_COUNT_FULL-1 && row > 0){
			neighbours.add(bubbles.get(row-1).get(col));
		}
		if (!bubbles.get(row).isFull() && row > 0){
			neighbours.add(bubbles.get(row-1).get(col+1));
		}
		//LOWER LEFT
		if (bubbles.get(row).isFull() && col > 0 && row < ROW_COUNT-1){
			neighbours.add(bubbles.get(row+1).get(col-1));
		}
		if (!bubbles.get(row).isFull() && row < ROW_COUNT-1){
			neighbours.add(bubbles.get(row+1).get(col));
		}
		//LOWER RIGHT
		if (bubbles.get(row).isFull() && col < COL_COUNT_FULL-1 && row < ROW_COUNT-1){
			neighbours.add(bubbles.get(row+1).get(col));
		}
		if (!bubbles.get(row).isFull() && row < ROW_COUNT-1){
			neighbours.add(bubbles.get(row+1).get(col+1));
		}
		return neighbours;
	}
	
	// returns the distance of the two bubbles
	public static double BubbleDist(Bubble b1, Bubble b2){
		double x_dist = b1.getCenterLocation().x-b2.getCenterLocation().x;
		double y_dist = b1.getCenterLocation().y-b2.getCenterLocation().y;
		return Math.sqrt(Math.pow(x_dist, 2)+Math.pow(y_dist, 2));
	}
	
	// removes the bubbles that are coherent with the recently placed one
	private int removeCoherent(int row, int col){
		unMarkAll();
		markColor(row, col);
		int ret = 0;
		if(countMarked()>2){
			playSoundEffect("C:\\\\Users\\\\FATIMA WASEEM\\\\OneDrive - Higher Education Commission\\\\Desktop\\\\bubble_shooter-master1\\\\bubble_shooter\\pop.wav");
			ret = countMarked();
			removeMarked();
		}
		unMarkAll();
		score+=ret*SCORE_COHERENT;
		return ret;
	}
	
	// removes all bubbles that would anyway just float in the grid
	private int removeFloating(){
		markAll();
		for (Bubble b : bubbles.get(0)){
			if(b.isVisible()){
				unMarkNotFloating(b.getRow(), b.getCol());
			}
		}

		int ret = countMarked();
		removeMarked();
		unMarkAll();
		score+=ret*SCORE_FLOATING;
		return ret;
	}

	public void playSoundEffect(String filepath) {
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filepath));
			Clip soundClip = AudioSystem.getClip();
			soundClip.open(audioStream);
			soundClip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// unmarks not floating elements outgoing from the bubble
	private void unMarkNotFloating(int row, int col){
		bubbles.get(row).get(col).unmark();
		for (Bubble b : getNeighbours(row, col)){
			if (b.isMarked() && b.isVisible()){
				unMarkNotFloating(b.getRow(), b.getCol());
			}
		}
	}
	
	// marks the bubbles that have the same color
	private void markColor(int row, int col){
		bubbles.get(row).get(col).mark();
		for (Bubble b : getNeighbours(row, col)){
			if(b.isVisible() && !b.isMarked()){
				if (b.getColor().equals(bubbles.get(row).get(col).getColor())){
					markColor(b.getRow(), b.getCol());
				}
			}
		}
	}
	
	// counts the marked bubbles in the bubble-matrix
	private int countMarked() {
		int ret = 0;
		for(RowList r : bubbles){
			for(Bubble b : r){
				if (b.isMarked() && b.isVisible()){
					ret++;
				}
			}
		}
		return ret;
	}
	
	// unmarks all bubbles
	private void unMarkAll(){
		for(RowList r : bubbles){
			for(Bubble b : r){
				b.unmark();
			}
		}
	}
	
	//marks all bubbles
	private void markAll(){
		for(RowList r : bubbles){
			for(Bubble b : r){
				b.mark();
			}
		}
	}
	
	// removes all marked bubbles
	private void removeMarked(){
		for(RowList r : bubbles){
			for(Bubble b : r){
				if(b.isMarked()){
					b.setVisible(false);
				}
			}
		}
	}
	
	// returns whether the game is stopped or running
	public boolean isStopped() {
		return stopped;
	}
	
	// stops the game
	public void stop(){
		stopped = true;
	}
	
	// returns the initial number of rows of the game 
	public int getInitialRows(){
		return initial_rows;
	}
	
	// returns the number of colors used in the game to color bubbles
	public int getColors(){
		return colors;
	}
	
	// returns the score currently achieved by the player
	public long getScore(){
		return score;
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		moving_bubble.move();
		checkProximity();
		canvas.repaint();
	}
}

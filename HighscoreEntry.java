package bubble_shooter;
import java.io.*;
import java.util.*;

//  a class that can store an entry of highscore
 
public class HighscoreEntry implements Serializable, Comparable<HighscoreEntry>, Comparator<HighscoreEntry>{
	private String name;
	private long score;
	private int rows;
	private int color;
	
	
	//  constructor for the class
	public HighscoreEntry(String name, long score, int rows, int color) {
		this.name = name;
		this.score = score;
		this.rows = rows;
		this.color = color;
	}
	
	
	//  getter for the name	 	 
	public String getName() {
		return name;
	}
	
	//  setter for the name	 
	 
	public void setName(String name) {
		this.name = name;
	}
	
	//  getter for score	 
	public long getScore() {
		return score;
	}
	
	//  setter for score
	public void setScore(long score) {
		this.score = score;
	}
	
	//  getter for rows
	public int getRows() {
		return rows;
	}
	
	//  setter for rows
	public void setRows(int rows) {
		this.rows = rows;
	}
	
	//  getter for color
	public int getColor() {
		return color;
	}
	
	//  setter for color	 
	public void setColor(int color) {
		this.color = color;
	}
	
	
	//  serialisation
	//  o the objectouptupstream to write to
	 
	public void writeObject(ObjectOutputStream o) throws IOException{
		o.writeObject(name);
		o.writeLong(score);
		o.writeInt(rows);
		o.writeInt(color);
	}
	
	
	//  deserialisation
	//  o the objectinputstream to read from
	 
	public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException{
		name = (String) o.readObject();
		score = (long) o.readLong();
		rows = (int) o.readInt();
		color = (int) o.readInt();
	}
	
	
	@Override
	public int compareTo(HighscoreEntry other) {
		if(score > other.getScore()) return -1;
		if(score < other.getScore()) return 1;
		return 0;
	}
	
	
	@Override
	public int compare(HighscoreEntry h1, HighscoreEntry h2) {
		if(h1.score > h2.getScore()) return -1;
		if(h1.score < h2.getScore()) return 1;
		return 0;
	}
}

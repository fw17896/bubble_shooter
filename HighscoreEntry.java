package bubble_shooter;
import java.io.*;

//  it store an entry of highscore
public class HighscoreEntry implements Serializable, Comparable<HighscoreEntry>{
	private String name;
	private long score;
	private int rows;
	private int color;
		
	//  constructor
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
	
	
	//  serialization to write
	public void writeObject(ObjectOutputStream o) throws IOException{
		o.writeObject(name);
		o.writeLong(score);
		o.writeInt(rows);
		o.writeInt(color);
	}
	
	
	//  deserialisation to read from
	public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException{
		name = (String) o.readObject();
		score = (long) o.readLong();
		rows = (int) o.readInt();
		color = (int) o.readInt();
	}
	
	// for natural ordering.
	@Override
	public int compareTo(HighscoreEntry other) {
		if(score > other.getScore()) return -1;
		if(score < other.getScore()) return 1;
		return 0;
	}

}

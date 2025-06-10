package bubble_shooter;
import java.io.*;
import java.util.*;
import javax.swing.table.AbstractTableModel;

//   a class that can store the whole set of highscored and provides, a tablemodell for the table
public class Highscores extends AbstractTableModel implements Serializable{	 
	ArrayList<HighscoreEntry> entries = new ArrayList<HighscoreEntry>();
	
	// Returns the number of columns in the table
	@Override
	public int getColumnCount() {
		return 4;
	}
	
	// Returns the number of rows in the table, equal to the number of entries 
	@Override
	public int getRowCount() {
		return entries.size();
	}

	// Returns the value at a specific row and column in the table
	@Override
	public Object getValueAt(int row, int col) {
		HighscoreEntry entry = entries.get(row);
		switch (col) {
			case 0: 
				return entry.getName();
			case 1: 
				return entry.getScore();
			case 2: 
				return entry.getRows();
			default: 
				return entry.getColor();
		}
	}
	
	// Returns the name of a specific column 
	@Override
	public String getColumnName(int index){
		switch(index) {
			case 0: 
				return "Name";
			case 1: 
				return "Score";
			case 2: 
				return "#rows";
			default: 
				return "#colors";
		}
	}

	// Returns the class type of a specific column (used by JTable for rendering)
	@Override
	public Class getColumnClass(int index){
		switch(index) {
			case 0: 
				return String.class;
			case 1: 
				return Long.class;
			case 2: 
				return Integer.class;
			default: 
				return Integer.class;
		}
	}


	// Specifies that cells are not editable
	@Override
	public boolean isCellEditable(int rowIndex, int colIndex) {
		return false;
	}
	
	
	// Adds a high score entry and sorts the list
	public void addEntry(HighscoreEntry e){
		entries.add(e);
		Collections.sort(entries);
	}
	
	
	//   serialisation
	public void writeObject(ObjectOutputStream o) throws IOException{
		o.writeObject(entries);
	}
	
	
	//   deserialisation
	public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException{
		entries = (ArrayList<HighscoreEntry>) o.readObject();
	}
	
	
	//   prints all entries on the standard output, only exists
	public void print(){
		for (HighscoreEntry h : entries){
			System.out.println(h.getName()+" "+h.getScore()+" "+h.getRows()+" "+h.getColor());
		}
	}
	
}

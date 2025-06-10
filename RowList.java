package bubble_shooter;
import java.util.ArrayList;

public class RowList extends ArrayList<Bubble> {
	private boolean full;
	
	//  constructor, sets if the row a full row is
	public RowList(boolean full) {
		this.full = full;
	}
	
	// returns whether the row is a full row
	public boolean isFull(){
		return full;
	}
	
	//  sets the row to a full row
	public void setFull(){
		full=true;
	}
	
}

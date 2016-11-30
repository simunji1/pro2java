package game;

import java.util.ArrayList;

public class ListWalls extends ArrayList<Wall> {
	private int which;
	
	public ListWalls() {
		super();
		which = 0;
	}
	
	@Override
	public void clear() {
		super.clear();
		which = 0;
	}
	
	public Wall getActualWall() {
		return this.get(which);
	}
	
	public Wall getPreviousWall() {
		return this.get(which - 1 < 0 ? this.size() - 1 : which - 1);
	}
	
	public void nextWall() {
		//which = which < this.size() - 1 ? which++ : 0;
		if (which == this.size()-1) which = 0;
		else which++;
	}
}

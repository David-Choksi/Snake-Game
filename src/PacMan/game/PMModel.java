package PacMan.game;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;


import java.util.Random;
import java.util.Set;


public class PMModel {
	private PacMan pacMan = new PacMan();
	private RowCol location = new RowCol();
	
	
	
	
	
	public void pmDirectionChange(String direction){
		pacMan.changeDirection(direction);
	}
	
	public String pmGetDirection(){
		return pacMan.getDirection();
	}
}

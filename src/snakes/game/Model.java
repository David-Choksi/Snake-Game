package snakes.game;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;


import java.util.Random;
import java.util.Set;


public class Model {
	public int row;
	public int col;
	public Map<String, Integer> values = new TreeMap<String, Integer>();
	
	public Model (int rows, int cols){
		this.row = rows;
		this.col = cols;
		int value = 1;
		for (int i = 0; i<rows-1; i++){
			for (int j = 0; j<cols; j++){
				values.put("" + i + j, value);
				value++;
			}
		}
		for (int k = 0; k<cols-1; k++){
			values.put("" + (rows-1) + k, value);
			value++;
		}
		values.put("" + (rows-1)+(cols-1), 0);
	}
	

	public int rows(){
		return this.row;
	}
	
	public int cols(){
		return this.col;
	}
	
	public int getValue(RowCol loc){
		return values.get("" + loc.row() + loc.col());
	}
	
	public boolean isEmpty(RowCol loc){
		if (getValue(loc)==0){
			return true;
		}
		return false;
	}
	
	public RowCol getEmpty(){
		for (int i = 0; i<this.row; i++){
			for (int j =0; j<this.col; j++){
				if (values.get(""+i+j)==0){
					RowCol newOne = new RowCol(i,j);
					return newOne;
				}
			}
		}
		return null;
	}
	public void shuffle(){
		int max = this.row * this.col;

		Random rng = new Random(); // Ideally just create one instance globally
		// Note: use LinkedHashSet to maintain insertion order
		Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() < max)
		{
		    Integer next = rng.nextInt(max);
		    // As we're adding to a set, this will automatically do a containment check
		    generated.add(next);
		}
		int val = 0;
		for (int i = 0; i<this.row; i++){
			for (int j = 0; j<this.col; j++){
				values.put("" + i + j, (int) generated.toArray()[val]);
				val++;
			}
		}

	}
	public boolean isSolved(){
		Model model = new Model(this.row,this.col);
		if (model.equals(this)){
			return true;
		}
		return false;
	}
	
	public boolean move(RowCol loc){
		if (isEmpty(loc)){
			return false;
		}
		RowCol position = new RowCol(loc);
		position = loc.nextCol();
		if (position.col()< this.cols() && position.col()>=0){
			if (isEmpty(position)){
				values.put("" + position.row() + position.col(), values.get("" + loc.row() + loc.col()));
				values.put("" + loc.row() + loc.col(), 0);
				return true;
			}
		}
		position = loc.prevCol();
		if (position.col()<= this.cols() && position.col()>=0){
			if (isEmpty(position)){
				values.put("" + position.row() + position.col(), values.get("" + loc.row() + loc.col()));
				values.put("" + loc.row() + loc.col(), 0);
				return true;
			}
		}
		position = loc.nextRow();
		if (position.row()< this.rows() && position.row()>=0){
			if (isEmpty(position)){
				values.put("" + position.row() + position.col(), values.get("" + loc.row() + loc.col()));
				values.put("" + loc.row() + loc.col(), 0);
				return true;
			}
		}
		position = loc.prevRow();
		if (position.row()< this.rows() && position.row()>=0){
			if (isEmpty(position)){
				values.put("" + position.row() + position.col(), values.get("" + loc.row() + loc.col()));
				values.put("" + loc.row() + loc.col(), 0);
				return true;
			}
		}
		return false;		
	}
	@Override
	public boolean equals(Object obj){
		if (obj == null){
			return false;
		}
		if (!(obj instanceof Model)){
			return false;
		}
		Model model = (Model)obj;
		for (int i = 0; i<this.row; i++){
			for (int j = 0; j<this.col; j++){
				if (!(values.get(""+i+j)==model.values.get("" + i + j))){
					return false;
				}
			}
		}
		return true;
	}

}

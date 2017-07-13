package PacMan.game;

import java.util.HashMap;
import java.util.Map;

public class PMGameBoard {
	
	private Map<RowCol,Integer> food = new HashMap<RowCol,Integer>();
	
	public PMGameBoard(){
		reSetBoard();
	}
	public void reSetBoard(){
		//SuperFood has 2
		food.put(new RowCol(3,1), 2);
		food.put(new RowCol(3,26), 2);
		food.put(new RowCol(23,1), 2);
		food.put(new RowCol(23,26), 2);
		//Regular food has 1
		for (int i = 1; i<30; i++){
			if (i<9 || i>19){
				if (!(i==24 || i == 25)){
					food.put(new RowCol(i, 1), 1);
					food.put(new RowCol(i, 26), 1);
				}
			}
		}
		//SuperFood has 2
		food.put(new RowCol(3,1), 2);
		food.put(new RowCol(3,26), 2);
		food.put(new RowCol(23,1), 2);
		food.put(new RowCol(23,26), 2);
		for (int i = 1; i<30; i++){
			if (i<9 || i>19){
				if (i== 1 || i == 5 || i == 8 || i==20 || i==23 || i==26 || i == 29){
					food.put(new RowCol(i, 2), 1);
					food.put(new RowCol(i, 25), 1);
				}
			}
		}
		for (int i = 1; i<30; i++){
			if (i<9 || i>19){
				if (i== 1 || i == 5 || i == 8 || i==20 || i==23 || i == 24|| i ==25|| i==26 || i == 29){
					food.put(new RowCol(i, 3), 1);
					food.put(new RowCol(i, 24), 1);
				}
			}
		}	
		for (int i = 1; i<30; i++){
			if (i<9 || i>19){
				if (i== 1 || i == 5 || i == 8 || i==20 || i==26 || i == 29){
					food.put(new RowCol(i, 4), 1);
					food.put(new RowCol(i, 23), 1);
					food.put(new RowCol(i, 5), 1);
					food.put(new RowCol(i, 22), 1);
				}
			}
		}	
		for (int i = 1; i<30; i++){
			
			if (i>= 1 && (!(i==27)) && (!(i==28))){
				food.put(new RowCol(i, 6), 1);
				food.put(new RowCol(i, 21), 1);
			}

		}	
		for (int i = 1; i<30; i++){
			if (i<9 || i>19){
				if (i== 1 || i == 5 || i==20|| i == 29){
					food.put(new RowCol(i, 7), 1);
					food.put(new RowCol(i, 20), 1);
					food.put(new RowCol(i, 8), 1);
					food.put(new RowCol(i, 19), 1);
				}
			}
		}	
		for (int i = 1; i<30; i++){
			if (i<9 || i>19){
				if (i== 1 || i == 5|| i==6 ||i == 7 || i ==8 || i==20|| i==24||i ==25 ||i==26|| i == 29){
					food.put(new RowCol(i, 9), 1);
					food.put(new RowCol(i, 18), 1);
				}
			}
		}
		for (int i = 1; i<30; i++){
			if (i<9 || i>19){
				if (i== 1 || i == 5|| i ==8 || i==20||i==26|| i == 29){
					food.put(new RowCol(i, 10), 1);
					food.put(new RowCol(i, 17), 1);
					food.put(new RowCol(i, 11), 1);
					food.put(new RowCol(i, 16), 1);
				}
			}
		}
		for (int i = 1; i<30; i++){
			if (i<9 || i>19){
				if (i== 1 || i == 2|| i ==3 || i==4||i==5|| i == 8|| i == 20|| i ==21 || i==22||i>25){
					food.put(new RowCol(i, 12), 1);
					food.put(new RowCol(i, 15), 1);
				}
			}
		}
		for (int i = 1; i<30; i++){
			if (i<9 || i>19){
				if (i==5||i==29){
					food.put(new RowCol(i, 13), 1);
					food.put(new RowCol(i, 14), 1);
				}
			}
		}
	}
	
	public int getFood(int row, int col){
		RowCol key = new RowCol(row, col);
		if (food.get(key) == null){
			return 0;
		}
		return food.get(key);
	}
	
	public void setFood(int row, int col){
		RowCol key = new RowCol(row, col);
		food.remove(key);
	}
	
	public static void main(String[] args){
		PMGameBoard game = new PMGameBoard();
		for (int i =1; i<27; i++){
			System.out.println(game.getFood(1, i));

		}
		
	}
}










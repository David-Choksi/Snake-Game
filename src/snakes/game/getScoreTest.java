package snakes.game;
import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class getScoreTest {
	

	@Test
	public void test_Standard() {
		
		Model memes = new Model();
		RowCol coord = new RowCol(24, 35);
		Snake testSnake = new Snake (coord, coord, "left", "player1");
		
		testSnake.setLength(4);
		
		memes.setCounter(10);
	
		assertEquals(20, memes.getScore(testSnake));
		
		
	}
	
	@Test
	public void test_Big_Numbers() {
		
		Model memes = new Model();
		RowCol coord = new RowCol(24, 35);
		Snake testSnake = new Snake (coord, coord, "left", "player1");
		
		testSnake.setLength(9847);
		
		memes.setCounter(435);
	
		assertEquals(4282575, memes.getScore(testSnake));
		
	}
	
	@Test
	public void test_Boundary_Lower_No_Negatives() {
		
		Model memes = new Model();
		RowCol coord = new RowCol(24, 35);
		Snake testSnake = new Snake (coord, coord, "left", "player1");
		
		testSnake.setLength(2);
		
		memes.setCounter(0);
	
		assertEquals(0, memes.getScore(testSnake));
		
	}
	
	@Test
	public void test_Close_To_Boundary_Lower() {
		
		Model memes = new Model();
		RowCol coord = new RowCol(24, 35);
		Snake testSnake = new Snake (coord, coord, "left", "player1");
		
		testSnake.setLength(3);
		
		memes.setCounter(1);
	
		assertEquals(1, memes.getScore(testSnake));
		
	}
	
	@Test
	public void test_Standard_2() {
		
		Model memes = new Model();
		RowCol coord = new RowCol(24, 35);
		Snake testSnake = new Snake (coord, coord, "left", "player1");
		
		testSnake.setLength(3);
		
		memes.setCounter(1);
	
		assertEquals(1, memes.getScore(testSnake));
		
	}
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void test_Negative_Exception() {
		
		Model memes = new Model();
		RowCol coord = new RowCol(24, 35);
		Snake testSnake = new Snake (coord, coord, "left", "player1");
		
		testSnake.setLength(1);
		
		memes.setCounter(1);
		
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("There cannot be negative values");
	
		memes.getScore(testSnake);
		
	}
	
	@Test
	public void test_Negative_Exception_Snake_Length() {
		
		Model memes = new Model();
		RowCol coord = new RowCol(24, 35);
		Snake testSnake = new Snake (coord, coord, "left", "player1");
		
		testSnake.setLength(-4);
		
		memes.setCounter(1);
		
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("There cannot be negative values");
	
		memes.getScore(testSnake);
		
	}
	
	@Test
	public void test_Negative_Exception_Counter() {
		
		Model memes = new Model();
		RowCol coord = new RowCol(24, 35);
		Snake testSnake = new Snake (coord, coord, "left", "player1");
		
		testSnake.setLength(4);
		
		memes.setCounter(-9);
		
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("There cannot be negative values");
	
		memes.getScore(testSnake);
		
	}
	
	@Test
	public void test_Negative_Exception_Both_Values() {
		
		Model memes = new Model();
		RowCol coord = new RowCol(24, 35);
		Snake testSnake = new Snake (coord, coord, "left", "player1");
		
		testSnake.setLength(-6);
		
		memes.setCounter(-9);
		
		expectedEx.expect(RuntimeException.class);
		expectedEx.expectMessage("There cannot be negative values");
	
		memes.getScore(testSnake);
		
	}
	
	@Test
	public void test_Upper_Boundary() {
		
		Model memes = new Model();
		RowCol coord = new RowCol(24, 35);
		Snake testSnake = new Snake (coord, coord, "left", "player1");
		
		testSnake.setLength(999999);
		
		memes.setCounter(9999999);
	
		assertEquals(1000000000, memes.getScore(testSnake));
		
	}
	

}

package softwareEngineeringProject;
import java.util.Scanner;

public class Player {
	public int points;
	Board playerBoard = new Board();
	Board opponentBoard = new Board();
	
	Player() {
		points = 0;
		}
	
	public void setBoard(Scanner input) {
		int xcoordinate = 0;
		int ycoordinate = 0;
		System.out.println("Please enter a valid x coordinate.");
		xcoordinate = input.nextInt();
		System.out.println("Please enter a valid y coordinate.");
		ycoordinate = input.nextInt();
		playerBoard.setChar(xcoordinate, ycoordinate, 'b');
	}
	
	public void Attack(Player opponent, Scanner input) {
		opponentBoard.print();
		int xcoordinate = 0;
		int ycoordinate = 0;
		System.out.println("Select an x-coordinate for attack:");
		xcoordinate = CheckCoordinate(xcoordinate, input);
		System.out.println("Select a y-coordinate for attack:");
		ycoordinate = CheckCoordinate(ycoordinate, input);
		
		if (opponent.playerBoard.getChar(xcoordinate, ycoordinate) == 'b') {
			System.out.println("You hit something!");
			opponent.playerBoard.setChar(xcoordinate, ycoordinate, 'x');
			opponentBoard.setChar(xcoordinate, ycoordinate, 'x');
			points++;
		}
		else if (opponent.playerBoard.getChar(xcoordinate, ycoordinate) == 'x') {
			System.out.println("You've already hit a ship here.");
		}
		else {
			opponentBoard.setChar(xcoordinate, ycoordinate, 'o');
			System.out.println("You missed!");
		}
		
		
	}
	
	public int CheckCoordinate(int coordinate, Scanner input) {
		while (!input.hasNextInt()) {
			System.out.println("Not a valid coordinate!");
			System.out.println("Select a number from 1-8");
			input.next();
			
		}
		while(input.hasNextInt()) {
			coordinate = input.nextInt();
			if (coordinate < 1 || coordinate > 8) {
				System.out.println("Not a valid coordinate!");
				System.out.println("Select a number from 1-8");
			}
			else
				break;
		}
		return coordinate;
	}
	
}

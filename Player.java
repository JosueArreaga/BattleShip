package softwareEngineeringProject;
import java.util.Scanner;

public class Player {
	private static int SHIP_SIZE_2 = 2;
	private static int SHIP_SIZE_3 = 3;
	private static int SHIP_SIZE_4 = 4;
	
	
	public int points;
	Board playerBoard = new Board();
	Board opponentBoard = new Board();
	
	Player() {
		points = 0;
		}
	
	public void SetBoard(Scanner input) {
		System.out.println("Begin by selecting a starting coordinate for a ship of size 2.");
		ShipPlacement(input, SHIP_SIZE_2);
		playerBoard.print();
		System.out.println("Now let's select a starting coordinate for a ship of size 3.");
		ShipPlacement(input, SHIP_SIZE_3);
		playerBoard.print();
		System.out.println("And finally, let's set a starting coordinate for a ship of size 4.");
		ShipPlacement(input, SHIP_SIZE_4);
		playerBoard.print();
	}
	
	public void Attack(Player opponent, Scanner input) {
		opponentBoard.print();
		int xcoordinate = 0;
		int ycoordinate = 0;
		System.out.println("Select an x-coordinate for attack:");
		xcoordinate = CheckCoordinate(input, xcoordinate);
		System.out.println("Select a y-coordinate for attack:");
		ycoordinate = CheckCoordinate(input, ycoordinate);
		
		if (opponent.playerBoard.getChar(ycoordinate, xcoordinate) == 'b') {
			System.out.println("You hit something!");
			opponent.playerBoard.setChar(ycoordinate, xcoordinate, 'x');
			opponentBoard.setChar(ycoordinate, xcoordinate, 'x');
			points++;
		}
		else if (opponent.playerBoard.getChar(ycoordinate, xcoordinate) == 'x') {
			System.out.println("You've already hit a ship here.");
		}
		else {
			opponentBoard.setChar(ycoordinate, xcoordinate, 'o');
			System.out.println("You missed!");
		}
		
		
	}
	
	public int CheckCoordinate(Scanner input, int coordinate) {
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
	
	public void SetShipHorizontal(int shipSize, int xcoordinate, int ycoordinate) {
		for (int i = 1; i <= shipSize; i++) {
			playerBoard.setChar(ycoordinate, xcoordinate, 'b');
			xcoordinate++;
		}
	}
	
	public void SetShipVertical(int shipSize, int xcoordinate, int ycoordinate) {
		for (int i = 1; i <= shipSize; i++) {
			playerBoard.setChar(ycoordinate, xcoordinate, 'b');
			ycoordinate++;
		}
	}
	
	public boolean CheckShipHorizontal(int shipSize, int xcoordinate, int ycoordinate) {
		int i = 1;
		while (i <= shipSize) {
			if (xcoordinate > 8) {
				return false;
			}
			else if (playerBoard.getChar(ycoordinate, xcoordinate) != '~') {
				System.out.println("A ship is already in placed here!");
				return false;
			}
			else {
				xcoordinate++;
				i++;
			}
		}
		
			return true;
	}
	
	public boolean CheckShipVertical(int shipSize, int xcoordinate, int ycoordinate) {
		int i = 1;
		while (i <= shipSize) {
			if (ycoordinate > 8) {
				return false;
			}
			else if (playerBoard.getChar(ycoordinate, xcoordinate) != '~') {
				System.out.println("A ship is already in placed here!");
				return false;
			}
			else {
				ycoordinate++;
				i++;
			}
		}
		
			return true;
	}
	
	public boolean AskShipOrientation(Scanner input) {
		System.out.println("Would you like to place your ship vertically or horizontally? Enter v or h ");
		while(input.hasNext()) {
			char keyPress = input.next().charAt(0);
			System.out.println(keyPress);
			if (keyPress != 'h' && keyPress != 'v') {
				System.out.println("Please enter v for vertical placement, or h for horizontal placement.");
			}
			else if (keyPress == 'h')
				return true;
			else if (keyPress == 'v')
				return false;
		}
		return true;
	}
	
	public void ShipPlacement(Scanner input, int shipSize) {
		int xcoordinate = 0;
		int ycoordinate = 0;
		boolean validPlacement = false;
			while(validPlacement == false) {
				System.out.println("Please enter an x-coordinate from 1-8.");
				xcoordinate = CheckCoordinate(input, xcoordinate);
				System.out.println("Please enter an y-coordinate from 1-8.");
				ycoordinate = CheckCoordinate(input, ycoordinate);
				if (AskShipOrientation(input) == true) {
					if (CheckShipHorizontal(shipSize, xcoordinate, ycoordinate) == true) {
						SetShipHorizontal(shipSize, xcoordinate, ycoordinate);
						validPlacement = true;
					}
					else {
						System.out.println("Ship placement invalid! Try again.");
					}
				
				}
				else {
					if (CheckShipVertical(shipSize, xcoordinate, ycoordinate) == true) {
						SetShipVertical(shipSize, xcoordinate, ycoordinate);
						validPlacement = true;
					}
					else {
						System.out.println("Ship placement invalid! Try again.");
					}
				
				}
			}
	}
	
}

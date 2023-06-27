/*
 * This class contains all the functions pertaining to the player of the game.
 * Within it, there are functions to set-up the player's board, attack their
 * opponent's board, and input-handling to ensure correct values are provided by the user.
 * It handles the 'pieces' of the board, and how players manipulate them, so to speak.
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player {
    protected static int SHIP_SIZE_2 = 2;
    protected static int SHIP_SIZE_3 = 3;
    protected static int SHIP_SIZE_4 = 4;
    public static int POINTS_FOR_VICTORY = SHIP_SIZE_2 + SHIP_SIZE_3 + SHIP_SIZE_4;


    public int points;
    Board playerBoard = new Board();
    Board opponentBoard = new Board();
    ArrayList<Integer> attackCoordinates;

    Player() {
        points = 0;
        attackCoordinates = new ArrayList<>();
        for(int i = 11; i < 89; i++){
            if(i%10 != 9)
                attackCoordinates.add(i);
        }
    }
    //This function calls the ship placement function to walk the player through the process of placing
    //their ships on the board. It can call the ship placement function as many times as we desire,
    //allowing for more ships to be placed and extending the length of the game.
    public void SetBoard(Scanner input) {
        playerBoard.print();
        System.out.println("Begin by selecting a starting coordinate for a ship of size 2.");
        System.out.println("Ex: bb");
        ShipPlacement(input, SHIP_SIZE_2);
        playerBoard.print();
        System.out.println("Now let's select a starting coordinate for a ship of size 3.");
        System.out.println("Ex: bbb");
        ShipPlacement(input, SHIP_SIZE_3);
        playerBoard.print();
        System.out.println("And finally, let's set a starting coordinate for a ship of size 4.");
        System.out.println("Ex: bbbb");
        ShipPlacement(input, SHIP_SIZE_4);
        playerBoard.print();
    }

    /*
     * This is our main ship setup function. It sets a valid Placement boolean to false, and asks for a set of coordinates
     * which is then checked to ensure it exists on the board. The user is then asked how they wish to orient
     * the ship from this initial position. The function checks that the ship fits entirely on the board from
     * the initial position given the desired orientation, and sets the spaces with the 'b' signifier,
     * indicating a ship is placed there. If at any point an invalid input is given, or the ship cannot be
     * placed at the desired location, the process begins from the top, with selecting an initial starting point.
     * Otherwise, the boolean for validPlacement is set to true, ending the loop.
     */
    public void ShipPlacement(Scanner input, int shipSize) {
        int xcoordinate = 0;
        int ycoordinate = 0;
        boolean validPlacement = false;
        while(validPlacement == false) {
            System.out.println("Please enter an x-coordinate from 1-8.");
            xcoordinate = CheckCoordinate(input, xcoordinate);
            System.out.println("Please enter a y-coordinate from 1-8.");
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

    /*This function asks the user to input a coordinate to attack their opponent's board.
     * The coordinates are checked for validity, then passed to a getter method which
     * checks the opponents playerBoard at the given coordinates. If the space is occupied by a
     * ship, an 'x' is stored in the opponentBoard belonging to this specific Player object.
     * In this way, we can keep track of a player's actions without editing or
     * revealing the opponent's actual board in the process. If the player hits a target, they're given a point.
     */
    public void Attack(Player opponent, Scanner input) {
        //opponentBoard.print();
        int xcoordinate = 0;
        int ycoordinate = 0;
        System.out.println("Select an x-coordinate for attack:");
        xcoordinate = CheckCoordinate(input, xcoordinate);
        System.out.println("Select a y-coordinate for attack:");
        ycoordinate = CheckCoordinate(input, ycoordinate);

        if (opponent.playerBoard.getChar(ycoordinate, xcoordinate) == 'b') {
            System.out.println("You hit something!\n");
            opponent.playerBoard.setChar(ycoordinate, xcoordinate, 'x');
            opponentBoard.setChar(ycoordinate, xcoordinate, 'x');
            points++;
        }
        else if (opponent.playerBoard.getChar(ycoordinate, xcoordinate) == 'x') {
            System.out.println("You've already hit a ship here.\n");
        }
        else {
            opponentBoard.setChar(ycoordinate, xcoordinate, 'o');
            System.out.println("You missed!\n");
        }


    }

    //This function validates that a given input for coordinates is within the range
    //of acceptable values, those being the numbers 1 thru 8. If the input is invalid,
    //the user is prompted to re-enter a valid value.
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

    /*This function lays the ship onto the board horizontally.
     * It increments the x-coordinate until the number of spaces equal
     * to the shipSize parameter have been filled with a ship.
     */
    public void SetShipHorizontal(int shipSize, int xcoordinate, int ycoordinate) {
        for (int i = 1; i <= shipSize; i++) {
            playerBoard.setChar(ycoordinate, xcoordinate, 'b');
            xcoordinate++;
        }
    }
    /*This function lays the ship onto the board vertically.
     * It increments the y-coordinate until the number of spaces equal
     * to the shipSize parameter have been filled with a ship.
     */
    public void SetShipVertical(int shipSize, int xcoordinate, int ycoordinate) {
        for (int i = 1; i <= shipSize; i++) {
            playerBoard.setChar(ycoordinate, xcoordinate, 'b');
            ycoordinate++;
        }
    }

    /*
     * This function verifies that the ship can be placed horizontally at a given point,
     * so long as the ship does not run off the board, or intersect with another ship that
     * has already been placed. If either of these conditions occurs, the function returns false.
     * Otherwise, the ship placement is legal, and the function returns true.
     */
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

    /*
     * This function verifies that the ship can be placed vertically at a given point,
     * so long as the ship does not run off the board, or intersect with another ship that
     * has already been placed. If either of these conditions occurs, the function returns false.
     * Otherwise, the ship placement is legal, and the function returns true.
     */
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

    /*
     * This function asks the user their desired orientation for their ship placement.
     * If vertical, the function returns false, if horizontal it returns true. We use this
     * result in our ShipPlacement function to determine whether we call SetShipHorizontal
     * or SetShipVertical. This function also validates user input, ensuring a 'v' or 'h' is entered.
     */
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

    /*
        This method receives the rows of the board as a string after being appended with a "/" after each row.
        We can then utilize .split("/") to create string arrays of the board rows.
     */
    public void combinedBoard(){
        StringBuilder sb = new StringBuilder();
        String [] p1 = playerBoard.getBoardRows().split("/");
        String [] p2 = opponentBoard.getBoardRows().split("/");

        for(int i = 0; i < p1.length; i++){
            sb.setLength(0);
            System.out.println(sb.append(p1[i]).append("            ").append(p2[i]));
        }
    }



    /*
        This method allows for a QuickStart. It places ships in the RandomShipPlacement method.
     */
    public void SetBoardRandomly() {
        RandomShipPlacement(SHIP_SIZE_2);
        RandomShipPlacement(SHIP_SIZE_3);
        RandomShipPlacement(SHIP_SIZE_4);
    }

    /*
        This method is similar to ShipPlacement with a few differences. Firstly, there is no need for user input, nor a need for
        coordinate verification. The reason for this, is that the random class automatically produces values within the valid range.
        It then iterates randomly through the board until all the ships are placed.
     */
    public void RandomShipPlacement(int shipSize) {
        boolean validPlacement = false;
        Random rnd = new Random();
        while(validPlacement == false) {
            int xCoordinate = rnd.nextInt(8) + 1; // this well enter coordinates 100% safe
            int yCoordinate = rnd.nextInt(8) + 1; // this will enter coordinates 100% safe
            int position = rnd.nextInt(2); // this produce values 0 - 1. We can use 0 to represent horizontal and 1 to represent vertical


            if (position == 0) {
                if (CheckShipHorizontal(shipSize, xCoordinate, yCoordinate) == true) {
                    SetShipHorizontal(shipSize, xCoordinate, yCoordinate);
                    validPlacement = true;
                }
            }
            else {
                if (CheckShipVertical(shipSize, xCoordinate, yCoordinate) == true) {
                    SetShipVertical(shipSize, xCoordinate, yCoordinate);
                    validPlacement = true;
                }
            }
        }
    }

}
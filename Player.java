/*
 * This class contains all the functions pertaining to the player of the game.
 * Within it, there are functions to set-up the player's board, attack their
 * opponent's board, and input-handling to ensure correct values are provided by the user.
 * It handles the 'pieces' of the board, and how players manipulate them, so to speak.
 */

import java.util.*;

public class Player {
    public int ship1;
    public int ship2;
    public int ship3;
    public int ship4;
    public boolean superAttackActive = true;

    ArrayList<Set<Integer>> boats = new ArrayList<Set<Integer>>();
    Board playerBoard = new Board();
    Board opponentBoard = new Board();

    Player() {
    }

    public void DeclareShipLengths(Scanner input) {
        System.out.println("How long would you like your first ship to be? Choose a number between 1-6");
        ship1 = CheckShipLength(input, ship1);
        System.out.println("How long would you like your second ship to be? Choose a number between 1-6");
        ship2 = CheckShipLength(input, ship2);
        System.out.println("How long would you like your third ship to be? Choose a number between 1-6");
        ship3 = CheckShipLength(input, ship3);
        System.out.println("How long would you like your fourth ship to be? Choose a number between 1-6");
        ship4 = CheckShipLength(input, ship4);
    }

    public int CheckShipLength(Scanner input, int length) {
        do {
            System.out.println("Enter a number from 1-6.");
            while(!input.hasNextInt()) {
                System.out.println("Not a valid length! Please enter a number from 1-6.");
                input.next();
            }
            length = input.nextInt();
        } while (length < 1 || length > 6);

        return length;
    }


    //This function calls the ship placement function to walk the player through the process of placing
    //their ships on the board. It can call the ship placement function as many times as we desire,
    //allowing for more ships to be placed and extending the length of the game.
    public void SetBoard(Scanner input) {
        playerBoard.print();
        DeclareShipLengths(input);

        System.out.println("Begin by selecting a starting coordinate for your first ship.");
        ShipPlacement(input, ship1);
        playerBoard.print();

        System.out.println("Now let's select a starting coordinate for your second ship.");
        ShipPlacement(input, ship2);
        playerBoard.print();

        System.out.println("Select a starting coordinate for your third ship.");
        ShipPlacement(input, ship3);
        playerBoard.print();

        System.out.println("Finally, let's set a starting coordinate for your fourth ship.");
        ShipPlacement(input, ship4);
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
        while(!validPlacement) {
            System.out.println("Please enter an x-coordinate from 1-8.");
            xcoordinate = CheckCoordinate(input, xcoordinate);
            System.out.println("Please enter a y-coordinate from 1-8.");
            ycoordinate = CheckCoordinate(input, ycoordinate);
            if(AskShipOrientation(input)) {
                if(CheckShipHorizontal(shipSize, xcoordinate, ycoordinate)) {
                    SetShipHorizontal(shipSize, xcoordinate, ycoordinate);
                    validPlacement = true;
                }
                else {
                    System.out.println("Ship placement invalid! Try again.");
                }

            }
            else {
                if(CheckShipVertical(shipSize, xcoordinate, ycoordinate)) {
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
        System.out.println("Debug hax \n");
        opponent.playerBoard.print();
        System.out.println("\n");
        int xcoordinate = 0;
        int ycoordinate = 0;
        do {
            System.out.println("Choose a new location to attack.");
            System.out.print("Select an x-coordinate! ");
            xcoordinate = CheckCoordinate(input, xcoordinate);
            System.out.print("Select a y-coordinate! ");
            ycoordinate = CheckCoordinate(input, ycoordinate);
            if (opponentBoard.getChar(ycoordinate, xcoordinate) == 'x' || opponentBoard.getChar(ycoordinate, xcoordinate) == 'o')
                System.out.println("You've already attacked this location!");
        } while (opponentBoard.getChar(ycoordinate, xcoordinate) == 'x' || opponentBoard.getChar(ycoordinate, xcoordinate) == 'o');

        char[] result = firing(opponent, (xcoordinate*10 + ycoordinate));

        if (result[0] == 'b') {
            System.out.println("You hit something!\n");
            if(result[1] == 't'){
                System.out.println("You sunk your opponent's fighting boat!");
            }
        }else {
            System.out.println("You missed!\n");
        }


    }

    public char[] firing(Player opponent, int coordinate) {
        int x = coordinate / 10;
        int y = coordinate % 10;
        char shot = opponent.playerBoard.getChar(y, x);
        boolean sunk = false;

        if (shot == 'b') {
            opponent.playerBoard.setChar(y, x, 'x');
            opponentBoard.setChar(y, x, 'x');
            for(int i = 0; i < opponent.boats.size(); i++){
                if(opponent.boats.get(i).contains(coordinate)){
                    opponent.boats.get(i).remove(coordinate);
                    if(opponent.boats.get(i).size() == 0) {
                        System.out.println("You sunk my fighting boat!\n");
                        sunk = true;
                        opponent.boats.remove(i);
                    }
                }
            }
        } else if (shot == '~') {
            opponent.playerBoard.setChar(y, x, 'o');
            opponentBoard.setChar(y, x, 'o');
        }
        if(sunk){
            return new char[]{shot, 't'};
        }
        return new char[]{shot, 'f'};
    }

    public void SuperAttack(Player opponent, Scanner input) {
        int ycoordinate = 1;
        int xcoordinate = 1;
        int hitTotal = 0;
        int sinkTotal = 0;
        char orientation;
        superAttackActive = false;

        System.out.println("Horizontal or Vertical super shot? h for horizontal, v for vertical.");
        //take input for h or v

        while(!input.hasNext("[hv]")) {
            System.out.println("Please enter h for horizontal, or v for vertical.");
            input.next();
        }

        orientation = input.next().charAt(0);

        if(orientation == 'h'){
            System.out.println("Select row to obliterate: ");
            ycoordinate = CheckCoordinate(input, ycoordinate);
            while(xcoordinate < 9){
                char[] results = firing(opponent, xcoordinate*10 + ycoordinate);
                if(results[0] == 'b') {
                    hitTotal++;
                    if(results[1] == 't'){
                        sinkTotal++;
                    }
                }
                xcoordinate++;
            }
        } else if(orientation == 'v'){
            System.out.println("Select column to obliterate: ");
            xcoordinate = CheckCoordinate(input, xcoordinate);
            while(ycoordinate < 9){
                char[] results = firing(opponent, xcoordinate*10 + ycoordinate);
                if(results[0] == 'b') {
                    hitTotal++;
                    if(results[1] == 't'){
                        sinkTotal++;
                    }
                }
                ycoordinate++;
            }
        }

        if(hitTotal > 2){
            System.out.println("Excellent super shot commander! " + hitTotal + " hits!\n");
        } else if(hitTotal > 1){
            System.out.println("You got " + hitTotal + " hits!\n");
        } else if(hitTotal > 0){
            System.out.println("You got a hit!\n");
        } else{
            System.out.println("No hits!\n");
        }

        if(sinkTotal > 1){
            System.out.println("You sunk " + sinkTotal + "boats! That must've hurt!\n");
        } else if(sinkTotal > 0){
            System.out.println("Good job sinking an enemy ship!\n");
        }

    }

    //This function validates that a given input for coordinates is within the range
    //of acceptable values, those being the numbers 1 thru 8. If the input is invalid,
    //the user is prompted to re-enter a valid value.
    public int CheckCoordinate(Scanner input, int coordinate) {
        do {
            System.out.println("Enter a number from 1-8.");
            while(!input.hasNextInt()) {
                System.out.println("Not a valid coordinate! Please enter a number from 1-8.");
                input.next();
            }
            coordinate = input.nextInt();
        } while (coordinate < 1 || coordinate > 8);

        return coordinate;
    }

    /*This function lays the ship onto the board horizontally.
     * It increments the x-coordinate until the number of spaces equal
     * to the shipSize parameter have been filled with a ship.
     */
    public void SetShipHorizontal(int shipSize, int xcoordinate, int ycoordinate) {
        Set<Integer> boat = new HashSet<Integer>();
        //changed from i =1  and i <= shipSize
        for (int i = 0; i < shipSize; i++) {
            playerBoard.setChar(ycoordinate, xcoordinate, 'b');
            boat.add((xcoordinate * 10) + ycoordinate);
            xcoordinate++;
        }
        boats.add(boat);
    }

    /*This function lays the ship onto the board vertically.
     * It increments the y-coordinate until the number of spaces equal
     * to the shipSize parameter have been filled with a ship.
     */
    public void SetShipVertical(int shipSize, int xcoordinate, int ycoordinate) {
        Set<Integer> boat = new HashSet<Integer> ();
        for (int i = 1; i <= shipSize; i++) {
            playerBoard.setChar(ycoordinate, xcoordinate, 'b');
            boat.add((xcoordinate * 10) + ycoordinate );
            ycoordinate++;
        }
        boats.add(boat);
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
        while(!input.hasNext("[vh]")) {
            System.out.println("Please enter v for vertical placement, or h for horizontal placement.");
            input.next();
        }
        char keyPress = input.next().charAt(0);
        if (keyPress == 'h')
            return true;
        else
            return false;
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
        RandomShipPlacement(2);
        RandomShipPlacement(3);
        RandomShipPlacement(3);
        RandomShipPlacement(4);
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

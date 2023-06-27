import java.util.Random;

public class AI extends Player{

    public AI(){
        SetBoardRandomly();
    }

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


    public void RandomAttack(Player opponent) {

        Random rnd = new Random();
        int randomVal = rnd.nextInt(attackCoordinates.size()); // we pick a random value the size of the board
        int coordinate = attackCoordinates.get(randomVal);    //  we pick a random target area

        int xcoordinate = coordinate/10;
        int ycoordinate = coordinate%10;

        if (opponent.playerBoard.getChar(ycoordinate, xcoordinate) == 'b') {
            System.out.println("Your Opponent Hit you!!!\n");
            opponent.playerBoard.setChar(ycoordinate, xcoordinate, 'x');
            opponentBoard.setChar(ycoordinate, xcoordinate, 'x');
            points++;
        }
        else {
            opponentBoard.setChar(ycoordinate, xcoordinate, 'o');
            opponent.playerBoard.setChar(ycoordinate, xcoordinate, 'o');
            System.out.println("Your Opponent missed\n");
        }

        attackCoordinates.remove(randomVal); // we remove the attacked square to avoid duplicate hits.
    }
}

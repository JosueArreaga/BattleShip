import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AI extends Player {
    int prevHit = -1;
    int prevShot = -1;
    //+1 = North, ,-10 = East -1 = South, +10 = West
    ArrayList<Integer> directions;
    Random rand = new Random();
    int directFound = 0;
    int level;
    //Player computerPlayer = new Player();
    ArrayList<Integer> attackCoordinates = new ArrayList<>();

    AI(int difficulty) {
        //Might be simpler to just have 11-88 and then remove offending numbers
        for (int i = 11; i < 89; i++) {
            if (i % 10 != 9)
                attackCoordinates.add(i);
        }
        level = difficulty;
        directions.add(1);
        directions.add(-10);
        directions.add(-1);
        directions.add(10);
    }
    //Smart AI:
    //Store the first hit on a ship
    //Start randomly attacking around it until it gets a hit
    //Once it does, continue going down that path until it misses or sinks a ship
    //If it misses, continue randomly attacking the other directions (keep a list of rand directions, [N,S,E,W]
    //which get deleted as it hits or misses on each)
    //If it sinks, reset first hit value until it hits another ship randomly.


    public void attack(Player opponent){
        if(level == 1 && prevHit > 10){
            if(directFound!= 0 && attackCoordinates.contains(prevHit + directFound)){
                int newShot = prevHit + directFound;
                firing(opponent, newShot);
                prevHit = newShot;
                attackCoordinates.remove(newShot);

            } else{
                boolean isValid = false;
                //Continues until it runs out of directions or gets a valid coordinate
                while(!isValid && directions.size() > 0){
                    int index = rand.nextInt(directions.size());
                    //gets a new coordinate by adding the previous with a direction
                    int newShot = prevHit + directions.get(index);
                    //Looks for that coordinate in the attack coordinates set
                    if(attackCoordinates.contains(newShot)){
                        //Sets the previous shot variable to the new shot
                        //Sets isvalid to true to stop the looping
                        prevShot = newShot;
                        isValid = true;
                        //Removes that coordinate from the set
                        attackCoordinates.remove(newShot);
                        //Attacks the coordinate
                        if(firing(opponent, newShot) == 'b'){
                            directFound = directions.get(index);
                        }
                    }
                    //Removes that direction from the index at the end
                    directions.remove(index);
                }
            }
        } else {
            prevShot = RandomAttack(opponent);
            if(prevShot != -1){
                prevHit = prevShot;
            }
        }
    }

    public int RandomAttack(Player opponent) {

        int targetHit = -1;
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
            targetHit = coordinate;
        }
        else {
            opponentBoard.setChar(ycoordinate, xcoordinate, 'o');
            System.out.println("Your Opponent missed\n");
        }

        attackCoordinates.remove(randomVal); // we remove the attacked square to avoid duplicate hits.
        return targetHit;
    }

}
